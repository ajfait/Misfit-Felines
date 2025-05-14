package com.misfit.util;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.misfit.auth.*;
import org.apache.logging.log4j.*;
import org.apache.commons.io.*;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class responsible for handling authentication using Amazon Cognito.
 *
 * <p>This class provides methods to:
 * <ul>
 *     <li>Construct a token request to Cognito using an authorization code</li>
 *     <li>Parse and validate ID tokens using JWKS</li>
 *     <li>Extract user claims (e.g., username) from the token</li>
 * </ul>
 * </p>
 *
 * <p>Configuration properties required for operation:
 * <ul>
 *     <li>{@code client.id}</li>
 *     <li>{@code client.secret}</li>
 *     <li>{@code oauthURL}</li>
 *     <li>{@code redirectURL}</li>
 *     <li>{@code region}</li>
 *     <li>{@code poolId}</li>
 * </ul>
 * </p>
 */
public class Auth {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final String CLIENT_ID;
    private final String CLIENT_SECRET;
    private final String OAUTH_URL;
    private final String REDIRECT_URL;
    private final String REGION;
    private final String POOL_ID;

    private Keys jwks;

    /**
     * Constructs a new {@code Auth} utility instance and loads the Cognito JWKS keys.
     *
     * @param properties the properties file containing required Cognito configuration values
     */
    public Auth(Properties properties) {
        this.CLIENT_ID = properties.getProperty("client.id");
        this.CLIENT_SECRET = properties.getProperty("client.secret");
        this.OAUTH_URL = properties.getProperty("oauthURL");
        this.REDIRECT_URL = properties.getProperty("redirectURL");
        this.REGION = properties.getProperty("region");
        this.POOL_ID = properties.getProperty("poolId");

        loadKey();
    }

    /**
     * Sends the token request to Amazon Cognito and maps the JSON response into a {@link TokenResponse} object.
     *
     * @param authRequest the prepared {@link HttpRequest} to the Cognito token endpoint
     * @return a {@link TokenResponse} object containing ID, access, and refresh tokens
     * @throws IOException if there is an issue sending the request or reading the response
     * @throws InterruptedException if the request is interrupted during execution
     */
    public TokenResponse getToken(HttpRequest authRequest) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<?> response = client.send(authRequest, HttpResponse.BodyHandlers.ofString());

        logger.debug("Response status: " + response.statusCode());
        logger.debug("Response headers: " + response.headers());
        logger.debug("Response body: " + response.body());

        ObjectMapper mapper = new ObjectMapper();
        TokenResponse tokenResponse = mapper.readValue(response.body().toString(), TokenResponse.class);
        logger.debug("Id token: " + tokenResponse.getIdToken());

        return tokenResponse;
    }

    /**
     * Validates the JWT token using Cognito's public key and extracts the Cognito username claim.
     *
     * @param tokenResponse the token response object containing the ID token to validate
     * @return the Cognito username if the token is successfully verified
     * @throws IOException if decoding the token header fails
     */
    public String validate(TokenResponse tokenResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CognitoTokenHeader tokenHeader = mapper.readValue(
                CognitoJWTParser.getHeader(tokenResponse.getIdToken()).toString(),
                CognitoTokenHeader.class
        );

        String keyId = tokenHeader.getKid();
        String alg = tokenHeader.getAlg();

        // Select the first key (note: this assumes the first key matches; production should match by 'kid')
        BigInteger modulus = new BigInteger(1,
                org.apache.commons.codec.binary.Base64.decodeBase64(jwks.getKeys().get(0).getN()));
        BigInteger exponent = new BigInteger(1,
                org.apache.commons.codec.binary.Base64.decodeBase64(jwks.getKeys().get(0).getE()));

        PublicKey publicKey = null;
        try {
            publicKey = KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, exponent));
        } catch (InvalidKeySpecException e) {
            logger.error("Invalid Key Error: " + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Algorithm Error: " + e.getMessage(), e);
        }

        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);

        String iss = String.format("https://cognito-idp.%s.amazonaws.com/%s", REGION, POOL_ID);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(iss)
                .withClaim("token_use", "id")
                .build();

        DecodedJWT jwt = verifier.verify(tokenResponse.getIdToken());
        String userName = jwt.getClaim("cognito:username").asString();
        logger.debug("Username: " + userName);
        logger.debug("Claims: " + jwt.getClaims());

        return userName;
    }

    /**
     * Constructs an HTTP POST request to exchange the provided authorization code for Cognito tokens.
     *
     * @param authCode the authorization code received from the login redirect
     * @return a {@link HttpRequest} to the token endpoint with proper headers and form data
     */
    public HttpRequest buildAuthRequest(String authCode) {
        String keys = CLIENT_ID + ":" + CLIENT_SECRET;

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "authorization_code");
        parameters.put("client-secret", CLIENT_SECRET);
        parameters.put("client_id", CLIENT_ID);
        parameters.put("code", authCode);
        parameters.put("redirect_uri", REDIRECT_URL);

        String form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        String encoding = Base64.getEncoder().encodeToString(keys.getBytes());

        return HttpRequest.newBuilder()
                .uri(URI.create(OAUTH_URL))
                .headers("Content-Type", "application/x-www-form-urlencoded", "Authorization", "Basic " + encoding)
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();
    }

    /**
     * Loads the JSON Web Key Set (JWKS) used to validate JWTs issued by the configured Cognito User Pool.
     *
     * <p>This method retrieves and parses the JWKS from:
     * {@code https://cognito-idp.{region}.amazonaws.com/{poolId}/.well-known/jwks.json}</p>
     *
     * <p>It uses Jackson for JSON mapping and Apache Commons IO to handle the download.</p>
     */
    private void loadKey() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            URL jwksURL = new URL(String.format(
                    "https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json",
                    REGION, POOL_ID
            ));

            File jwksFile = new File("jwks.json");
            FileUtils.copyURLToFile(jwksURL, jwksFile);
            jwks = mapper.readValue(jwksFile, Keys.class);
            logger.debug("JWKS loaded. Example exponent: " + jwks.getKeys().get(0).getE());
        } catch (IOException ioException) {
            logger.error("Unable to load JWKS: " + ioException.getMessage(), ioException);
        } catch (Exception e) {
            logger.error("Unexpected error loading JWKS: " + e.getMessage(), e);
        }
    }
}