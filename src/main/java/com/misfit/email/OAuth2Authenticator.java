package com.misfit.email;

import jakarta.mail.*;

/**
 * The `OAuth2Authenticator` class stores and uses an access token provided during its creation
 * to return a `PasswordAuthentication` object with a specified username and access token.
 */
public class OAuth2Authenticator extends Authenticator {
    private String accessToken;

    /**
     * The `public OAuth2Authenticator(String accessToken)` constructor in the `OAuth2Authenticator` class
     * is initializing the `accessToken` variable with the value passed as a parameter to the constructor.
     * This allows the `OAuth2Authenticator` instance to store and use the access token provided during its
     * creation.
     */
    public OAuth2Authenticator(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * The `getPasswordAuthentication` function overrides the method to return a `PasswordAuthentication`
     * object with a specified username and access token.
     * 
     * @return A `PasswordAuthentication` object is being returned with the username "user" and the access
     * token stored in the variable `accessToken`.
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("user", accessToken);
    }
}