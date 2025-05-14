package com.misfit.util;

import java.util.Properties;

/**
 * Utility class for building the Amazon Cognito logout URL.
 *
 * <p>This class constructs a logout URL used to terminate the user's session
 * with Cognito and optionally redirect the user to a post-logout destination.</p>
 *
 * <p>The following properties must be present in the provided {@link Properties} object:
 * <ul>
 *     <li>{@code domain} - The base URL of your Cognito domain (e.g., {@code https://your-domain.auth.region.amazoncognito.com})</li>
 *     <li>{@code client.id} - The app client ID registered in Cognito</li>
 *     <li>{@code hosted} - The URL where the user should be redirected after logout</li>
 * </ul>
 * </p>
 *
 * <p>This utility is typically used in servlets to perform logout redirection.</p>
 *
 * @see com.misfit.controller.LogoutServlet
 */
public class Logout {
    private final Properties properties;

    /**
     * Constructs a {@code Logout} utility instance using the provided Cognito-related properties.
     *
     * @param properties a {@link Properties} object containing required keys for logout URL generation:
     *                   {@code domain}, {@code client.id}, and {@code hosted}
     */
    public Logout(Properties properties) {
        this.properties = properties;
    }

    /**
     * Builds the full Cognito logout URL using configured domain, client ID, and logout redirect URI.
     *
     * <p>The URL includes query parameters for:
     * <ul>
     *     <li>{@code client_id}</li>
     *     <li>{@code logout_uri}</li>
     * </ul>
     * and follows this structure:</p>
     *
     * <pre>
     * https://{domain}/logout?client_id={clientId}&logout_uri={hosted}
     * </pre>
     *
     * @return the complete logout URL as a {@code String}
     */
    public String buildLogoutUrl() {
        String domain = properties.getProperty("domain");
        String clientId = properties.getProperty("client.id");
        String hosted = properties.getProperty("hosted");

        return domain + "/logout?client_id=" + clientId + "&logout_uri=" + hosted;
    }
}