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

@WebServlet(
        urlPatterns = {"/logout"}
)
public class Logout extends HttpServlet implements PropertiesLoader {
    Properties properties;
    private final Logger logger = LogManager.getLogger(this.getClass());
    public static String DOMAIN;
    public static String CLIENT_ID;
    public static String HOSTED;

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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession() != null) {
            request.getSession().invalidate();
        }
        String cognitoLogoutUrl = DOMAIN
                + "/logout?client_id=" + CLIENT_ID
                + "&logout_uri=" + HOSTED;

        response.sendRedirect(cognitoLogoutUrl);
    }
}