package com.misfit.util;

import org.apache.logging.log4j.*;

import java.util.Properties;

/**
 * Utility class used for building the Amazon Cognito login URL.
 *
 * <p>This class uses application-level Cognito properties to construct
 * a login URL that initiates the OAuth 2.0 authorization flow. The URL includes
 * the response type, client ID, and redirect URI necessary for the authentication
 * handshake.</p>
 *
 * <p>Properties must include at least:
 * <ul>
 *     <li>{@code client.id}</li>
 *     <li>{@code loginURL}</li>
 *     <li>{@code redirectURL}</li>
 * </ul>
 * </p>
 *
 * <p>The constructed URL is typically used in login redirection within a servlet.</p>
 *
 * @see com.misfit.controller.LogInServlet
 */
public class LogIn {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final Properties properties;

    /**
     * Constructs a {@code LogIn} instance with the specified properties.
     *
     * @param properties a {@link Properties} object containing Cognito-related keys
     *                   such as {@code client.id}, {@code loginURL}, and {@code redirectURL}.
     */
    public LogIn(Properties properties) {
        this.properties = properties;
    }

    /**
     * Builds the Cognito login URL using the provided application properties.
     *
     * <p>The resulting URL includes query parameters for:
     * <ul>
     *     <li>{@code response_type=code}</li>
     *     <li>{@code client_id}</li>
     *     <li>{@code redirect_uri}</li>
     * </ul>
     * </p>
     *
     * @return a fully constructed Cognito login URL for redirection
     */
    public String buildLoginUrl() {
        String clientId = properties.getProperty("client.id");
        String loginUrl = properties.getProperty("loginURL");
        String redirectUrl = properties.getProperty("redirectURL");

        logger.debug("Client ID: " + clientId);
        logger.debug("Login URL: " + loginUrl);
        logger.debug("Redirect URL: " + redirectUrl);

        return loginUrl + "?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUrl;
    }
}