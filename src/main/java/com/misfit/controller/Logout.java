package com.misfit.controller;

import com.misfit.persistence.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * This servlet handles the logout functionality by redirecting the user to the Cognito logout URL.
 * The user will be logged out from the Cognito service.
 * 
 * @param request the HTTP request made by the user
 * @param response the HTTP response to be sent back to the user
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
        
        try {
            String cognitoLogoutUrl = DOMAIN + "/logout?client_id=" + CLIENT_ID + "&logout_uri=" + HOSTED;
            logger.debug("Routed to Cognito logout");
            response.sendRedirect(cognitoLogoutUrl);
        } catch (Exception e) {
            logger.error("Error routing to Cognito logout");
            response.sendRedirect("error.jsp");
        }
    }
}