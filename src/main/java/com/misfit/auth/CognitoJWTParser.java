package com.misfit.auth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.json.JSONObject;

import java.security.InvalidParameterException;

/**
 * Utility class for all operations on JWT.
 */
public class CognitoJWTParser {
    private static final int HEADER = 0;
    private static final int PAYLOAD = 1;
    private static final int SIGNATURE = 2;
    private static final int JWT_PARTS = 3;

    /**
     * Returns header for a JWT as a JSON object.
     *
     * @param jwt REQUIRED: valid JSON Web Token as String.
     * @return header as a JSONObject.
     */
    public static JSONObject getHeader(String jwt) {
        try {
            validateJWT(jwt);
            Base64.Decoder dec = Base64.getDecoder();
            final byte[] sectionDecoded = dec.decode(jwt.split("\\.")[HEADER]);
            final String jwtSection = new String(sectionDecoded, StandardCharsets.UTF_8);
            return new JSONObject(jwtSection);
        } catch (final Exception e) {
            throw new InvalidParameterException("error in parsing JSON");
        }
    }

    /**
     * Returns payload of a JWT as a JSON object.
     *
     * @param jwt REQUIRED: valid JSON Web Token as String.
     * @return payload as a JSONObject.
     */
    public static JSONObject getPayload(String jwt) {
        try {
            validateJWT(jwt);
            Base64.Decoder dec = Base64.getDecoder();
            final String payload = jwt.split("\\.")[PAYLOAD];
            final byte[] sectionDecoded = dec.decode(payload);
            final String jwtSection = new String(sectionDecoded, StandardCharsets.UTF_8);
            return new JSONObject(jwtSection);
        } catch (final Exception e) {
            throw new InvalidParameterException("error in parsing JSON");
        }
    }

    /**
     * Returns signature of a JWT as a String.
     *
     * @param jwt REQUIRED: valid JSON Web Token as String.
     * @return signature as a String.
     */
    public static String getSignature(String jwt) {
        try {
            validateJWT(jwt);
            Base64.Decoder dec = Base64.getDecoder();
            final byte[] sectionDecoded = dec.decode(jwt.split("\\.")[SIGNATURE]);
            return new String(sectionDecoded, StandardCharsets.UTF_8);
        } catch (final Exception e) {
            throw new InvalidParameterException("error in parsing JSON");
        }
    }

    /**
     * Returns a claim, from the {@code JWT}s' payload, as a String.
     *
     * @param jwt   REQUIRED: valid JSON Web Token as String.
     * @param claim REQUIRED: claim name as String.
     * @return claim from the JWT as a String.
     */
    public static String getClaim(String jwt, String claim) {
        try {
            final JSONObject payload = getPayload(jwt);
            final Object claimValue = payload.get(claim);

            if (claimValue != null) {
                return claimValue.toString();
            }

        } catch (final Exception e) {
            throw new InvalidParameterException("invalid token");
        }
        return null;
    }

    /**
     * Checks if {@code JWT} is a valid JSON Web Token.
     *
     * @param jwt REQUIRED: The JWT as a {@link String}.
     */
    public static void validateJWT(String jwt) {
        // Check if the the JWT has the three parts
        final String[] jwtParts = jwt.split("\\.");
        if (jwtParts.length != JWT_PARTS) {
            throw new InvalidParameterException("not a JSON Web Token");
        }
    }
}