package com.misfit.controller;

import com.misfit.entity.Person;
import com.misfit.persistence.GenericDAO;
import com.misfit.persistence.PropertiesLoader;
import com.misfit.service.CognitoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Add person.
 */
@WebServlet(
        name = "addPersonServlet",
        urlPatterns = {"/addPerson"}
)
public class AddPerson extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

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

            GenericDAO<Person> personDAO = new GenericDAO<>(Person.class);
            personDAO.insert(newPerson);
            logger.debug("person inserted");

            CognitoService cognitoService = new CognitoService();
            cognitoService.createUser(email);

            response.sendRedirect("success.jsp");

        } catch (Exception e) {
            logger.error("Error adding person", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");

        if (isAdmin == null || !isAdmin) {
            response.sendRedirect("unauthorized.jsp");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/add-person.jsp").forward(request, response);
    }
}