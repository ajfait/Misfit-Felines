package com.misfit.controller;

import com.misfit.entity.Cat;
import com.misfit.persistence.*;
import org.apache.logging.log4j.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * This servlet handles the GET request to view a list of cats.
 * It retrieves the list of cats from the database using a GenericDAO,
 * sets the attributes for the JSP page, and forwards the request to view-cats.jsp.
 * If an exception occurs during the process, it logs the error and redirects to an error page.
 *
 * @param request  the HttpServletRequest object
 * @param response the HttpServletResponse object
 * @throws IOException if an I/O error occurs
 */
@WebServlet(name = "viewCatServlet", urlPatterns = {"/viewCat"})
public class ViewCat extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles the HTTP GET request to display a list of cats.
     *
     * This method retrieves a list of cats from the database using a GenericDAO,
     * sets the list as an attribute in the request, sets the current page attribute,
     * and forwards the request to the view-cats.jsp page to display the list of cats.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            GenericDAO<Cat> catDAO = new GenericDAO<>(Cat.class);
            List<Cat> cats = catDAO.getAll();

            request.setAttribute("catList", cats);
            request.setAttribute("currentPage", "viewCat");

            request.getRequestDispatcher("view-cats.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error retrieving cat list", e);
            response.sendRedirect("error.jsp");
        }
    }
}
