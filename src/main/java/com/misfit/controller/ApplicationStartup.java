package com.misfit.controller;

import com.misfit.persistence.PropertiesLoader;
import com.misfit.util.*;
import org.apache.logging.log4j.*;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

/**
 * This class is responsible for initializing the application by loading required
 * properties and setting up the necessary authentication components. It reads
 * configuration properties from a file, sets them into the ServletContext,
 * and creates instances of the Auth, LogIn, and Logout utility classes for use
 * by other servlets in the application.
 * <p>
 * This servlet is loaded at application startup.
 * </p>
 *
 * <p>
 * It performs the following tasks:
 * </p>
 * <ul>
 *     <li>Loads the Cognito-related properties from a file.</li>
 *     <li>Stores the properties and authentication objects in the servlet context.</li>
 *     <li>Initializes instances of Auth, LogIn, and Logout classes to handle user authentication.</li>
 * </ul>
 *
 * <p>
 * This servlet is mapped to the URL pattern "/applicationStartup" and is loaded
 * at startup by the {@link javax.servlet.annotation.WebServlet} annotation with
 * the {@code loadOnStartup} parameter set to 1.
 * </p>
 */
@WebServlet(name = "applicationStartup", urlPatterns = {"/applicationStartup"}, loadOnStartup = 1)
public class ApplicationStartup extends HttpServlet implements PropertiesLoader {

    /**
     * The properties object holding the values loaded from the Cognito configuration file.
     */
    private Properties cognitoProperties;

    /**
     * The client ID for the Cognito authentication service.
     */
    private String CLIENT_ID;

    /**
     * The client secret for the Cognito authentication service.
     */
    private String CLIENT_SECRET;

    /**
     * The URL for OAuth2 authentication in Cognito.
     */
    private String OAUTH_URL;

    /**
     * The login URL for the Cognito authentication service.
     */
    private String LOGIN_URL;

    /**
     * The URL to redirect to after a successful login.
     */
    private String REDIRECT_URL;

    /**
     * The region where the Cognito service is hosted.
     */
    private String REGION;

    /**
     * The pool ID for the Cognito user pool.
     */
    private String POOL_ID;

    /**
     * The logger instance used for logging application events and errors.
     */
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Initializes the application by loading the Cognito properties, creating
     * instances of the Auth, LogIn, and Logout classes, and setting them in
     * the ServletContext for use by other servlets.
     * <p>
     * This method is called when the servlet is first loaded. It reads the
     * properties from the "cognito.properties" file, initializes the necessary
     * authentication components, and stores them in the servlet context for
     * future use.
     * </p>
     *
     * @throws ServletException if there is an error while loading properties
     *                          or initializing components.
     */
    @Override
    public void init() throws ServletException {
        super.init();

        logger.debug("ApplicationStartup INIT executed");

        // Load the properties file
        try {
            loadProperties();
            cognitoProperties.forEach((k, v) -> logger.debug(k + ": " + v));
        } catch (Exception e) {
            logger.error("Error loading properties: ", e);
            throw new ServletException("Error loading properties", e);
        }

        // Double-check that the properties were loaded
        if (cognitoProperties == null || cognitoProperties.isEmpty()) {
            logger.error("Cognito properties not loaded correctly.");
            throw new ServletException("Cognito properties not loaded correctly.");
        }

        // Set the properties into the ServletContext for other servlets
        getServletContext().setAttribute("cognitoProperties", cognitoProperties);

        // Create the Auth, LogIn, and Logout instances and set them in the context
        Auth auth = new Auth(cognitoProperties);
        LogIn login = new LogIn(cognitoProperties);
        Logout logout = new Logout(cognitoProperties);

        // Ensure each of these is properly set
        getServletContext().setAttribute("auth", auth);
        getServletContext().setAttribute("login", login);
        getServletContext().setAttribute("logout", logout);

        // Log that everything was successfully initialized
        logger.debug("ApplicationStartup successfully initialized.");
    }

    /**
     * Reads the Cognito properties file and sets the necessary values such as
     * the client ID, client secret, OAuth URL, login URL, redirect URL, region,
     * and pool ID for the Cognito authentication service.
     * <p>
     * This method loads the "cognito.properties" file, checks that all required
     * properties are loaded correctly, and throws an exception if any critical
     * properties are missing or if the file cannot be loaded.
     * </p>
     *
     * @throws ServletException if the properties cannot be loaded or if any
     *                          required property is missing.
     */
    private void loadProperties() throws ServletException {
        InputStream input = getClass().getClassLoader().getResourceAsStream("cognito.properties");
        if (input == null) {
            logger.error("Unable to find the cognito.properties file.");
            throw new ServletException("Unable to find the cognito.properties file.");
        }

        try {
            cognitoProperties = new Properties();
            cognitoProperties.load(input);
            CLIENT_ID = cognitoProperties.getProperty("client.id");
            CLIENT_SECRET = cognitoProperties.getProperty("client.secret");
            OAUTH_URL = cognitoProperties.getProperty("oauthURL");
            LOGIN_URL = cognitoProperties.getProperty("loginURL");
            REDIRECT_URL = cognitoProperties.getProperty("redirectURL");
            REGION = cognitoProperties.getProperty("region");
            POOL_ID = cognitoProperties.getProperty("poolId");

            // Ensure that all critical properties are loaded
            if (CLIENT_ID == null || CLIENT_SECRET == null || OAUTH_URL == null || LOGIN_URL == null) {
                logger.error("Missing required properties in cognito.properties.");
                throw new ServletException("Missing required properties in cognito.properties.");
            }

            logger.debug("Loaded properties: " + cognitoProperties);
        } catch (IOException e) {
            logger.error("Error loading cognito.properties file.", e);
            throw new ServletException("Error loading cognito.properties file.", e);
        }
    }
}