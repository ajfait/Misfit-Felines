package com.misfit.controller;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.annotation.WebServlet;

import org.apache.logging.log4j.*;

import com.misfit.auth.Keys;
import com.misfit.persistence.PropertiesLoader;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

@WebServlet(name = "ApplicationStartup", loadOnStartup = 1)
public class ApplicationStartup extends HttpServlet implements PropertiesLoader {
    private static final Logger logger = LogManager.getLogger(ApplicationStartup.class);

    Properties properties;
    String CLIENT_ID;
    String CLIENT_SECRET;
    String OAUTH_URL;
    String LOGIN_URL;
    String REDIRECT_URL;
    String REGION;
    String POOL_ID;
    Keys jwks;

    @Override
    public void init() throws ServletException {
        super.init();
        loadProperties();
    }

    /**
     * Read in the cognito props file and get/set the client id, secret, and required urls
     * for authenticating a user.
     */
    private void loadProperties() {
        try {
            properties = loadProperties("/cognito.properties");
            CLIENT_ID = properties.getProperty("client.id");
            CLIENT_SECRET = properties.getProperty("client.secret");
            OAUTH_URL = properties.getProperty("oauthURL");
            LOGIN_URL = properties.getProperty("loginURL");
            REDIRECT_URL = properties.getProperty("redirectURL");
            REGION = properties.getProperty("region");
            POOL_ID = properties.getProperty("poolId");
        } catch (IOException ioException) {
            logger.error("Cannot load properties..." + ioException.getMessage(), ioException);
        } catch (Exception e) {
            logger.error("Error loading properties" + e.getMessage(), e);
        }
    }    
}
