package com.misfit.email;

import jakarta.mail.*;

/**
 * The type O auth 2 authenticator.
 */
public class OAuth2Authenticator extends Authenticator {
    private String accessToken;

    /**
     * Instantiates a new O auth 2 authenticator.
     *
     * @param accessToken the access token
     */
    public OAuth2Authenticator(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("user", accessToken);
    }
}

