package com.misfit.email;

import jakarta.mail.*;

public class OAuth2Authenticator extends Authenticator {
    private String accessToken;

    public OAuth2Authenticator(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("user", accessToken);
    }
}

