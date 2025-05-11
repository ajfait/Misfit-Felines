package com.misfit.controller;

import com.misfit.entity.Person;
import com.misfit.persistence.GenericDAO;
import com.misfit.persistence.PropertiesLoader;
import com.misfit.service.CognitoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Delete person.
 */
@WebServlet(name = "deletePersonServlet", urlPatterns = {"/deletePerson"})
public class DeletePerson extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

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