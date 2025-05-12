package com.misfit.controller;

import com.misfit.entity.Cat;
import com.misfit.entity.Person;
import com.misfit.persistence.GenericDAO;
import com.misfit.persistence.PropertiesLoader;
import com.misfit.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * This servlet handles the editing of a person entity.
 * It retrieves the person ID from the request parameters, updates the corresponding person entity,
 * and redirects the user to either a success or error page based on the outcome.
 *
 * @param request  the HttpServletRequest object containing the request parameters
 * @param response the HttpServletResponse object used to redirect the user
 * @throws IOException if an I/O error occurs while processing the request
 */
@WebServlet(name = "editPersonServlet", urlPatterns = {"/editPerson"})
public class EditPerson extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles the GET request to load a person for editing based on the provided ID.
     *
     * @param request  the HttpServletRequest object containing the request parameters
     * @param response the HttpServletResponse object for sending the response
     * @throws IOException if an I/O error occurs while processing the request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int personId = Integer.parseInt(request.getParameter("id"));
            GenericDAO<Person> personDAO = new GenericDAO<>(Person.class);
            Person person = personDAO.getById(personId);

            request.setAttribute("person", person);
            request.setAttribute("currentPage", "editPerson");
            request.getRequestDispatcher("edit-person.jsp").forward(request, response);
            logger.debug("Person with ID {} loaded for edit", personId);

        } catch (Exception e) {
            logger.error("Error loading person for edit", e);
            response.sendRedirect("error.jsp");
        }
    }

    /**
     * Handles the HTTP POST request to update a person's information.
     *
     * @param request  The HTTP servlet request containing the person's updated information
     * @param response The HTTP servlet response to redirect to success or error page
     * @throws IOException If an input or output exception occurs while processing the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int personId = Integer.parseInt(request.getParameter("personId"));

            String firstName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String role = request.getParameter("role");
            String[] prefs = request.getParameterValues("preferences");
            String preferences = prefs != null ? String.join(",", prefs) : "";
            boolean admin = "true".equals(request.getParameter("admin"));

            GenericDAO<Person> personDAO = new GenericDAO<>(Person.class);
            Person person = personDAO.getById(personId);

            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setPhone(phone);
            person.setEmail(email);
            person.setRole(role);
            person.setPreferences(preferences);
            person.setAdmin(admin);

            personDAO.update(person);
            logger.debug("Person with ID {} updated", personId);
            response.sendRedirect("success.jsp");

        } catch (Exception e) {
            logger.error("Error updating person", e);
            response.sendRedirect("error.jsp");
        }
    }
}