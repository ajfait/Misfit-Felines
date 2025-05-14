package com.misfit.controller;

import com.misfit.auth.TokenResponse;
import com.misfit.entity.Person;
import com.misfit.persistence.GenericDAO;
import com.misfit.util.Auth;
import org.apache.logging.log4j.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.http.HttpRequest;

/**
 * The {@code AuthServlet} handles the authentication callback after a user logs in
 * through Amazon Cognito. It retrieves the authorization code from the request,
 * exchanges it for a token, validates it, and establishes a session with
 * user information.
 * <p>
 * This servlet is mapped to the {@code /auth} URL and is triggered after the
 * user successfully logs in via the Cognito login page.
 * </p>
 *
 * <p>
 * Responsibilities include:
 * </p>
 * <ul>
 *     <li>Retrieving the {@code code} query parameter from the OAuth callback.</li>
 *     <li>Requesting an ID token from Cognito using the authorization code.</li>
 *     <li>Validating the token and extracting the username (email).</li>
 *     <li>Fetching the associated {@code Person} entity from the database using the username.</li>
 *     <li>Storing user-related information in the HTTP session for future requests.</li>
 * </ul>
 *
 * <p>
 * If authentication fails or the required objects are not available,
 * the servlet redirects to an error page or the index page.
 * </p>
 *
 * @see com.misfit.util.Auth
 * @see com.misfit.persistence.GenericDAO
 * @see com.misfit.entity.Person
 */
@WebServlet(urlPatterns = {"/auth"})
public class AuthServlet extends HttpServlet {

    /**
     * Logger instance for logging debug and error messages related to the authentication process.
     */
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * DAO used for retrieving {@code Person} entities from the database.
     * Initialized for the {@code Person} class.
     */
    private GenericDAO<Person> personDAO = new GenericDAO<>(Person.class);

    /**
     * Handles the HTTP GET request from the Cognito authentication redirect.
     * <p>
     * It expects a {@code code} query parameter, which it exchanges for a token
     * to authenticate the user. Upon successful authentication, it retrieves
     * the user's information and stores it in the HTTP session.
     * </p>
     *
     * @param request  the {@link HttpServletRequest} containing the auth code
     * @param response the {@link HttpServletResponse} used to redirect or render error
     * @throws ServletException if the servlet context or properties are not properly initialized
     * @throws IOException      if there is an issue communicating with the token endpoint
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the Auth object from the ServletContext
        Auth auth = (Auth) getServletContext().getAttribute("auth");

        if (auth == null) {
            // If Auth object is not initialized, return an error
            logger.debug("Auth not initialized");
            request.setAttribute("errorMessage", "Authentication service not available.");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // Extract authorization code from the request parameters
        String authCode = request.getParameter("code");

        if (authCode == null) {
            // If no code is found, redirect to the home page
            logger.debug("Auth code not found");
            response.sendRedirect("index.jsp");
            return;
        } else {
            // Build and send the request to exchange the code for tokens
            HttpRequest authRequest = auth.buildAuthRequest(authCode);
            try {
                // Obtain and validate the token response
                TokenResponse tokenResponse = auth.getToken(authRequest);
                String userName = auth.validate(tokenResponse);  // Typically the email address

                // Store token and username in the session
                HttpSession session = request.getSession();
                session.setAttribute("idToken", tokenResponse.getIdToken());
                session.setAttribute("userName", userName);

                logger.debug("Session ID: " + request.getSession().getId());
                logger.debug("idToken: " + tokenResponse.getIdToken());
                request.setAttribute("userName", userName);

            } catch (IOException e) {
                logger.error("Error getting or validating the token: " + e.getMessage(), e);
                response.sendRedirect("error.jsp");
            } catch (InterruptedException e) {
                logger.error("Error getting token from Cognito oauth url " + e.getMessage(), e);
                response.sendRedirect("error.jsp");
            }
        }

        // Redirect to the viewCat page after successful authentication
        response.sendRedirect(request.getContextPath() + "/viewCat");
    }
}