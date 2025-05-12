package com.misfit.controller;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.annotation.WebServlet;

import org.apache.logging.log4j.*;

import com.misfit.auth.Keys;
import com.misfit.persistence.PropertiesLoader;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

/**
 * This servlet class, named "ApplicationStartup", is responsible for loading properties
 * during application startup. It extends HttpServlet and implements PropertiesLoader.
 * It loads properties such as CLIENT_ID, CLIENT_SECRET, OAUTH_URL, LOGIN_URL, REDIRECT_URL,
 * REGION, and POOL_ID from a properties file.
 * If an IOException occurs during the loading process, it logs an error message with the exception details.
 *
 * @WebServlet annotation specifies the configuration details for this servlet, including its name
 * and the order in which it should be loaded on startup.
 */
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

    /**
     * Initializes the servlet by loading properties after calling the superclass's init method.
     *
     * @throws ServletException if an exception occurs during initialization
     */
    @Override
    public void init() throws ServletException {
        super.init();
        loadProperties();
    }

    /**
     * Loads properties from a specified file path and assigns them to corresponding fields.
     * The properties include client ID, client secret, OAuth URL, login URL, redirect URL, region, and pool ID.
     * If an IOException occurs during the loading process, it is caught and logged as an error.
     * If any other exception occurs, it is also caught and logged as an error.
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