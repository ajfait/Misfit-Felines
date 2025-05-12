package com.misfit.controller;

import com.misfit.entity.Cat;
import com.misfit.persistence.*;
import org.apache.logging.log4j.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet for deleting a cat entity.
 * This servlet handles the POST request to delete a cat by its ID.
 * It logs the deletion action and redirects to the viewCat page upon success.
 * If an error occurs during the deletion process, it logs the error and redirects to an error page.
 */
@WebServlet(name = "deleteCatServlet", urlPatterns = {"/deleteCat"})
public class DeleteCat extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles the HTTP POST request to delete a cat entity by its ID.
     *
     * @param request  the HTTP servlet request containing the cat ID parameter
     * @param response the HTTP servlet response to redirect after deletion
     * @throws IOException if an I/O error occurs while processing the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int catId = Integer.parseInt(request.getParameter("catId"));

            GenericDAO<Cat> catDAO = new GenericDAO<>(Cat.class);
            Cat cat = catDAO.getById(catId);

            if (cat != null) {
                catDAO.delete(cat);
                logger.debug("Cat with ID {} deleted", catId);
            }

            response.sendRedirect("/viewCat");

        } catch (Exception e) {
            logger.error("Error deleting cat", e);
            response.sendRedirect("error.jsp");
        }
    }
}