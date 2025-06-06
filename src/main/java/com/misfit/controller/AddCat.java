package com.misfit.controller;

import com.misfit.entity.*;
import com.misfit.persistence.*;
import com.misfit.service.CatBreedService;
import com.misfit.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.apache.logging.log4j.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * Servlet to handle adding a new cat to the database.
 * This servlet extends HttpServlet and implements PropertiesLoader.
 * It handles the HTTP POST request to add a new cat to the database.
 * It sets attributes for person, peopleList, and currentPage, 
 * then forwards the request to add-cat.jsp.
 */
@WebServlet(name = "addCatServlet", urlPatterns = {"/addCat"})
public class AddCat extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles the HTTP POST request to add a new cat to the database.
     *
     * @param request The HTTP servlet request containing cat information
     * @param response The HTTP servlet response to redirect to success or error page
     * @throws IOException If an input or output exception occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            logger.debug("doPost started");
            request.setCharacterEncoding("UTF-8");

            String name = request.getParameter("cat_name");
            String breed = request.getParameter("breed");
            String sex = request.getParameter("sex");
            String dobString = request.getParameter("birthdate");
            LocalDate dob = null;
            try {
                if (dobString != null && !dobString.isBlank()) {
                    dob = LocalDate.parse(dobString);
                }
            } catch (DateTimeParseException e) {
                logger.warn("Invalid birthdate format: {}", dobString);
            }
            String bio = request.getParameter("bio");
            boolean adoptable = request.getParameter("adoptable") != null;
            String personIdParam = request.getParameter("personId");
            int personId = Integer.parseInt(personIdParam);
            GenericDAO<Person> personDAO = new GenericDAO<>(Person.class);
            Person person = personDAO.getById(personId);
            logger.debug("parameters received");

            Cat newCat = new Cat();
            newCat.setName(name);
            newCat.setBreed(breed);
            newCat.setSex(sex);
            newCat.setDob(dob);
            newCat.setBio(bio);
            newCat.setAdoptable(adoptable);
            newCat.setPerson(person);
            logger.debug("cat object created");

            Set<ConstraintViolation<Cat>> violations = ValidationUtil.validate(newCat);

            if (!violations.isEmpty()) {
                request.setAttribute("violations", violations);
                request.setAttribute("cat", newCat);
                request.getRequestDispatcher("add-cat.jsp").forward(request, response);
                return;
            }

            GenericDAO<Cat> catDAO = new GenericDAO<>(Cat.class);
            catDAO.insert(newCat);
            logger.debug("cat inserted");

            response.sendRedirect("success.jsp");

        } catch (Exception e) {
            logger.error("Error adding cat", e);
            response.sendRedirect("error.jsp");
        }
    }

    /**
     * Handles the HTTP GET request to add a new cat.
     *
     * Retrieves a list of people from the database and the person from the session.
     * If the person is not in the session, redirects to an unauthorized page.
     * Sets attributes for the person, people list, and current page.
     * Forwards the request to the add-cat.jsp page.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws IOException      if an input or output error occurs while the servlet is handling the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        GenericDAO<Person> personDAO = new GenericDAO<>(Person.class);
        List<Person> people = personDAO.getAll();

        Person person = (Person) request.getSession().getAttribute("person");

        if (person == null) {
            response.sendRedirect("unauthorized.jsp");
            return;
        }

        try {
            CatBreedService breedService = new CatBreedService();
            List<String> breedNames = breedService.getBreedNames();
            request.setAttribute("breeds", breedNames);
        } catch (Exception e) {
            request.setAttribute("breeds", List.of("Unable to load breeds"));
            logger.error("Error loading cat breeds", e);
        }

        String catIdParam = request.getParameter("catId");
        if (catIdParam != null) {
            try {
                int catId = Integer.parseInt(catIdParam);
                GenericDAO<Cat> catDAO = new GenericDAO<>(Cat.class);
                Cat cat = catDAO.getById(catId);

                if (cat != null) {
                    request.setAttribute("cat", cat);
                }
            } catch (NumberFormatException e) {
                logger.error("Invalid catId provided: " + catIdParam, e);
            }
        }

        request.setAttribute("person", person);
        request.setAttribute("peopleList", people);
        request.setAttribute("currentPage", "addCat");

        request.getRequestDispatcher("add-cat.jsp").forward(request, response);
    }
}
