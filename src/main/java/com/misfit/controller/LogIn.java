package com.misfit.controller;

import com.misfit.persistence.PropertiesLoader;
import org.apache.logging.log4j.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Properties;

/**
 * This servlet handles the GET request to the "/logIn" URL pattern.
 * It redirects the user to the Cognito login page with the necessary parameters.
 * If an error occurs during the redirection, it forwards the user to an error page.
 */
@WebServlet(urlPatterns = {"/logIn"})

public class LogIn extends HttpServlet implements PropertiesLoader {
    Properties properties;
    private final Logger logger = LogManager.getLogger(this.getClass());
    public static String CLIENT_ID;
    public static String LOGIN_URL;
    public static String REDIRECT_URL;

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
     * Handles the GET request to redirect the user to the Cognito login page.
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException If the servlet encounters difficulty
     * @throws IOException      If an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String url = LOGIN_URL + "?response_type=code&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URL;
            logger.debug("Routed to Cognito login");
            response.sendRedirect(url);
        } catch (Exception e) {
            logger.error("Error routing to Cognito login");
            response.sendRedirect("error.jsp");
        }
    }
}