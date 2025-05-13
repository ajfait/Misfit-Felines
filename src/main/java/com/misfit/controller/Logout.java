package com.misfit.controller;

import com.misfit.persistence.PropertiesLoader;
import org.apache.logging.log4j.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Properties;

/**
 * This servlet handles the logout functionality by redirecting the user to the Cognito logout URL.
 * The user will be logged out from the Cognito service.
 */
@WebServlet(urlPatterns = {"/logout"})
public class Logout extends HttpServlet implements PropertiesLoader {
    Properties properties;
    private final Logger logger = LogManager.getLogger(this.getClass());
    public static String DOMAIN;
    public static String CLIENT_ID;
    public static String HOSTED;

    /**
     * Initializes the servlet.
     *
     * @throws ServletException if an exception occurs that interrupts the servlet's normal operation
     */
    @Override
    public void init() throws ServletException {
        super.init();
        loadProperties();
    }

    private void loadProperties() {
        try {
            properties = loadProperties("/cognito.properties");
            DOMAIN = properties.getProperty("domain");
            CLIENT_ID = properties.getProperty("client.id");
            HOSTED = properties.getProperty("hosted");
        } catch (IOException ioException) {
            logger.error("Cannot load properties..." + ioException.getMessage(), ioException);
        } catch (Exception e) {
            logger.error("Error loading properties" + e.getMessage(), e);
        }
    }

    /**
     * Handles the GET request to log out the user by invalidating the session and redirecting to the Cognito logout URL.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws ServletException if there is a servlet exception
     * @throws IOException      if there is an input/output exception
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession() != null) {
            request.getSession().invalidate();
        }
        String cognitoLogoutUrl = DOMAIN + "/logout?client_id=" + CLIENT_ID + "&logout_uri=" + HOSTED;

        response.sendRedirect(cognitoLogoutUrl);
    }
}