package com.misfit.controller;

import com.misfit.entity.Person;
import com.misfit.persistence.GenericDAO;
import com.misfit.persistence.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Edit person.
 */
@WebServlet(
        name = "editPersonServlet",
        urlPatterns = {"/editPerson"}
)
public class EditPerson extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(EditPerson.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            int personId = Integer.parseInt(request.getParameter("id"));
            GenericDAO<Person> personDAO = new GenericDAO<>(Person.class);
            Person person = personDAO.getById(personId);

            request.setAttribute("person", person);
            request.getRequestDispatcher("/WEB-INF/add-person.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error loading person for edit", e);
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");

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

            response.sendRedirect("success.jsp");

        } catch (Exception e) {
            logger.error("Error updating person", e);
            response.sendRedirect("error.jsp");
        }
    }
}