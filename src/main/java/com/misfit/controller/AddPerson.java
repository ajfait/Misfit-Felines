package com.misfit.controller;

import com.misfit.entity.Person;
import com.misfit.persistence.*;
import com.misfit.service.CognitoService;
import com.misfit.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.apache.logging.log4j.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Set;

/**
 * Servlet for adding a person to the system.
 * Only accessible to users with admin privileges.
 */
@WebServlet(name = "addPersonServlet", urlPatterns = {"/addPerson"})
public class AddPerson extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles the HTTP POST request to add a new person to the database.
     *
     * @param request  The HTTP servlet request containing the person's information.
     * @param response The HTTP servlet response to redirect to the success page.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.debug("doPost started");
            request.setCharacterEncoding("UTF-8");

            String firstName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String role = request.getParameter("role");
            String[] prefs = request.getParameterValues("preferences");
            String preferences = prefs != null ? String.join(",", prefs) : "";
            boolean admin = "true".equals(request.getParameter("admin"));
            logger.debug("parameters received");

            Person newPerson = new Person();
            newPerson.setFirstName(firstName);
            newPerson.setLastName(lastName);
            newPerson.setPhone(phone);
            newPerson.setEmail(email);
            newPerson.setRole(role);
            newPerson.setPreferences(preferences);
            newPerson.setAdmin(admin);
            logger.debug("person object created");

            // Validate the person object
            Set<ConstraintViolation<Person>> violations = ValidationUtil.validate(newPerson);
            if (!violations.isEmpty()) {
                request.setAttribute("violations", violations);
                request.setAttribute("person", newPerson);
                request.getRequestDispatcher("add-person.jsp").forward(request, response);
                return;
            }

            // Check if the email already exists in the local database
            GenericDAO<Person> personDAO = new GenericDAO<>(Person.class);
            Person existingPerson = personDAO.getByField("email", email);
            if (existingPerson != null) {
                logger.warn("Email already exists in local DB: {}", email);
                request.setAttribute("dbError", "Email already exists in our system.");
                request.setAttribute("person", newPerson);
                request.getRequestDispatcher("add-person.jsp").forward(request, response);
                return;
            }

            // Check if the email exists in Cognito
            CognitoService cognitoService = new CognitoService();
            try {
                if (cognitoService.userExists(email)) {
                    logger.warn("Email already exists in Cognito: {}", email);
                    request.setAttribute("cognitoError", "Email already exists in Cognito.");
                    request.setAttribute("person", newPerson);
                    request.getRequestDispatcher("add-person.jsp").forward(request, response);
                    return;
                }

                // Create user in Cognito
                cognitoService.createUser(email);
                logger.debug("Cognito user created successfully.");
            } catch (Exception ex) {
                logger.error("Error checking or creating Cognito user", ex);
            }

            // Insert the person into the database
            personDAO.insert(newPerson);
            logger.debug("Person inserted into the database");

            // Redirect to success page
            response.sendRedirect("success.jsp");

        } catch (Exception e) {
            logger.error("Error adding person", e);
        }
    }

    /**
     * Handles the HTTP GET request to add a person.
     *
     * @param request  the HttpServletRequest object containing the request parameters
     * @param response the HttpServletResponse object for sending the response
     * @throws IOException      if an input or output error occurs while the servlet is handling the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");

        if (isAdmin == null || !isAdmin) {
            response.sendRedirect("unauthorized.jsp");
            return;
        }

        request.setAttribute("currentPage", "addPerson");
        request.getRequestDispatcher("add-person.jsp").forward(request, response);
    }
}
