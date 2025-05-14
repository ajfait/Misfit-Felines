package com.misfit.controller;

import com.misfit.util.Logout;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * The {@code LogoutServlet} is responsible for handling user logout functionality.
 *
 * <p>This servlet invalidates the current HTTP session (if it exists),
 * and redirects the user to the Cognito logout endpoint using a
 * {@link Logout} utility class.</p>
 *
 * <p>If the {@code Logout} object is not available in the {@code ServletContext},
 * it forwards the user to an error page.</p>
 *
 * <p>This servlet is mapped to the {@code /logout} URL.</p>
 *
 * @see com.misfit.util.Logout
 */
@WebServlet(urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    /**
     * Handles HTTP GET requests to log the user out of the application.
     *
     * <p>This method performs the following steps:
     * <ul>
     *     <li>Invalidates the current HTTP session, if it exists.</li>
     *     <li>Retrieves the {@link Logout} utility from the {@code ServletContext}.</li>
     *     <li>If the {@code Logout} object is found, it generates the logout URL and redirects the user to it.</li>
     *     <li>If the {@code Logout} object is not found, the request is forwarded to {@code error.jsp}.</li>
     * </ul>
     * </p>
     *
     * @param request  the {@link HttpServletRequest} containing the client's request
     * @param response the {@link HttpServletResponse} used to send the response
     * @throws ServletException if the servlet encounters an issue
     * @throws IOException      if an I/O error occurs during redirection
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Invalidate the session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Get Logout utility from ServletContext
        Logout logout = (Logout) getServletContext().getAttribute("logout");

        if (logout == null) {
            request.getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // Build logout URL and redirect
        String logoutUrl = logout.buildLogoutUrl();
        response.sendRedirect(logoutUrl);
    }
}