package com.misfit.controller;

import com.misfit.entity.*;
import com.misfit.persistence.*;
import org.apache.logging.log4j.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * This servlet handles the GET request to view a list of medical.
 * It retrieves the list of medical from the database using a GenericDAO,
 * sets the attributes for the JSP page, and forwards the request to view-medical.jsp.
 * If an exception occurs during the process, it logs the error and redirects to an error page.
 *
 * @param request  the HttpServletRequest object
 * @param response the HttpServletResponse object
 * @throws IOException if an I/O error occurs
 */
@WebServlet(name = "viewMedicalServlet", urlPatterns = {"/viewMedical"})
public class ViewMedical extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles the HTTP GET request to display a list of medical.
     *
     * This method retrieves a list of medical from the database using a GenericDAO,
     * sets the list as an attribute in the request, sets the current page attribute,
     * and forwards the request to the view-medical.jsp page to display the list of medical.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String catIdParam = request.getParameter("id");
            GenericDAO<Medical> medicalDAO = new GenericDAO<>(Medical.class);

            List<Medical> medicals;

            if (catIdParam != null && !catIdParam.isEmpty()) {
                int catId = Integer.parseInt(catIdParam);

                GenericDAO<Cat> catDAO = new GenericDAO<>(Cat.class);
                Cat cat = catDAO.getById(catId);

                if (cat != null) {
                    medicals = medicalDAO.getByFieldList("cat", cat);
                    request.setAttribute("cat", cat);
                } else {
                    response.sendRedirect("error.jsp");
                    return;
                }
            } else {
                medicals = medicalDAO.getAll();
            }

            request.setAttribute("medicalList", medicals);
            request.setAttribute("currentPage", "viewMedical");

            request.getRequestDispatcher("view-medical.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error retrieving medical list", e);
            response.sendRedirect("error.jsp");
        }
    }
}