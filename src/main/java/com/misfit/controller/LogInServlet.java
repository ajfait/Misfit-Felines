package com.misfit.controller;

import com.misfit.util.LogIn;
import org.apache.logging.log4j.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * The {@code LogInServlet} handles user login by redirecting the user
 * to the appropriate Amazon Cognito login URL.
 *
 * <p>
 * This servlet is triggered when a request is made to the {@code /logIn} URL.
 * It retrieves a preconfigured {@link LogIn} object from the {@code ServletContext},
 * builds the Cognito login URL, and redirects the user to that URL.
 * </p>
 *
 * <p>
 * If the {@code LogIn} object is not found in the context (which should have been
 * set during application startup), the servlet forwards the request to an error page.
 * </p>
 *
 * @see com.misfit.util.LogIn
 */
@WebServlet(urlPatterns = {"/logIn"})
public class LogInServlet extends HttpServlet {

    /**
     * Logger used to record debug-level and error-level messages related to login redirection.
     */
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles HTTP GET requests by initiating the login process.
     *
     * <p>
     * It retrieves the {@link LogIn} instance from the {@code ServletContext},
     * uses it to generate the Cognito login URL, and redirects the user to that URL.
     * </p>
     *
     * <p>
     * If the {@code LogIn} object is not initialized (e.g., application startup failed),
     * the request is forwarded to {@code error.jsp}.
     * </p>
     *
     * @param request  the {@link HttpServletRequest} object that contains the request the client made
     * @param response the {@link HttpServletResponse} object that contains the response the servlet returns
     * @throws ServletException if the request cannot be handled
     * @throws IOException      if an input or output error occurs while handling the request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the LogIn instance from ServletContext
        LogIn login = (LogIn) getServletContext().getAttribute("login");
        logger.debug("Login initialized");

        if (login == null) {
            // If login wasn't initialized, forward to an error page
            logger.debug("Login not initialized");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // Build login URL and redirect
        String url = login.buildLoginUrl();
        response.sendRedirect(url);
    }
}