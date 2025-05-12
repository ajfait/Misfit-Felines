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
import java.util.List;

/**
 * This servlet class, ViewPerson, is responsible for handling GET requests to "/viewPerson".
 * It retrieves a list of Person objects from the database using a GenericDAO, sets the list as a request attribute,
 * sets the current page attribute, and forwards the request to "view-persons.jsp" for rendering.
 * If an exception occurs during the process, it logs the error and redirects to "error.jsp".
 */
@WebServlet(name = "viewPersonServlet", urlPatterns = {"/viewPerson"})
public class ViewPerson extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles the HTTP GET request to retrieve a list of people and display them on a JSP page.
     *
     * @param request  the HttpServletRequest object containing the request parameters
     * @param response the HttpServletResponse object for sending the response
     * @throws IOException if an I/O error occurs while processing the request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            GenericDAO<Person> personDAO = new GenericDAO<>(Person.class);
            List<Person> people = personDAO.getAll();

            request.setAttribute("peopleList", people);
            request.setAttribute("currentPage", "viewPerson");

            request.getRequestDispatcher("view-persons.jsp").forward(request, response);
        } catch (Exception e) {
            logger.error("Error retrieving person list", e);
            response.sendRedirect("error.jsp");
        }
    }
}