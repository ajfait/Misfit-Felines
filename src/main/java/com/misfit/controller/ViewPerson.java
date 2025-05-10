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
 * The type View person.
 */
@WebServlet(name = "viewPersonServlet", urlPatterns = {"/viewPerson"})
public class ViewPerson extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

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
