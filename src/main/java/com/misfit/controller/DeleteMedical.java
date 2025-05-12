package com.misfit.controller;

import com.misfit.entity.Medical;
import com.misfit.persistence.*;
import org.apache.logging.log4j.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet for deleting a medical record based on the provided medical ID.
 * This servlet handles the POST request to delete a medical record.
 *
 * @param request  the HTTP servlet request containing the medical ID to be deleted
 * @param response the HTTP servlet response to redirect to success or error pages
 * @throws IOException if an input or output exception occurs while processing the request
 */
@WebServlet(name = "deleteMedicalServlet", urlPatterns = {"/deleteMedical"})
public class DeleteMedical extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles the HTTP POST request to delete a medical record based on the provided medical ID.
     *
     * @param request  The HTTP servlet request containing the medical ID parameter
     * @param response The HTTP servlet response to redirect to success or error page
     * @throws IOException if an input or output exception occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int medicalId = Integer.parseInt(request.getParameter("medicalId"));

            GenericDAO<Medical> medicalDAO = new GenericDAO<>(Medical.class);
            Medical medical = medicalDAO.getById(medicalId);

            if (medical != null) {
                medicalDAO.delete(medical);
                logger.debug("Medical with ID {} deleted", medicalId);
            }

            response.sendRedirect("success.jsp");

        } catch (Exception e) {
            logger.error("Error deleting medical", e);
            response.sendRedirect("error.jsp");
        }
    }
}