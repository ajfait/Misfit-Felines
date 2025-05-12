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
 * The type Logout.
 */
@WebServlet(urlPatterns = {"/logout"})
public class Logout extends HttpServlet implements PropertiesLoader {
    /**
     * The Properties.
     */
    Properties properties;
    private final Logger logger = LogManager.getLogger(this.getClass());
    /**
     * The constant DOMAIN.
     */
    public static String DOMAIN;
    /**
     * The constant CLIENT_ID.
     */
    public static String CLIENT_ID;
    /**
     * The constant HOSTED.
     */
    public static String HOSTED;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    /**
     * Route to the aws-hosted cognito logout page.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException
     * @throws IOException
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