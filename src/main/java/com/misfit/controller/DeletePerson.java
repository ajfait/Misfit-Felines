package com.misfit.controller;

import com.misfit.entity.Person;
import com.misfit.persistence.*;
import com.misfit.service.CognitoService;
import org.apache.logging.log4j.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet for deleting a person entity from the database.
 * This servlet handles the POST request to delete a person by their ID.
 * It uses a GenericDAO to interact with the database.
 * If the deletion is successful, the user is redirected to viewPerson page.
 * If an error occurs during the deletion process, the user is redirected to an error page.
 */
@WebServlet(name = "deletePersonServlet", urlPatterns = {"/deletePerson"})
public class DeletePerson extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles the HTTP POST request to delete a person entity based on the provided personId.
     *
     * @param request  the HTTP servlet request containing the personId parameter
     * @param response the HTTP servlet response to redirect to viewPerson or error.jsp
     * @throws IOException if an I/O error occurs while processing the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int personId = Integer.parseInt(request.getParameter("personId"));

            GenericDAO<Person> personDAO = new GenericDAO<>(Person.class);
            Person person = personDAO.getById(personId);

            if (person != null) {
                String email = person.getEmail();
                personDAO.delete(person);
                logger.debug("Person with ID {} deleted", personId);

                try {
                    CognitoService cognitoService = new CognitoService();
                    cognitoService.deleteUser(email);
                    logger.debug("Person deleted from Cognito");
                } catch (Exception e) {
                    logger.error("Error deleting person from Cognito", e);
                    response.sendRedirect("error.jsp");
                    return;
                }
            }

            response.sendRedirect("/viewPerson");

        } catch (Exception e) {
            logger.error("Error deleting person", e);
            response.sendRedirect("error.jsp");
        }
    }
}