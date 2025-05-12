package com.misfit.controller;

import com.misfit.entity.Medical;
import com.misfit.persistence.*;
import org.apache.logging.log4j.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Handles the GET request to edit a medical record.
 *
 * @param request  the HttpServletRequest object containing the request parameters
 * @param response the HttpServletResponse object for sending the response
 * @throws IOException if an I/O error occurs while processing the request
 */
@WebServlet(name = "editMedicalServlet", urlPatterns = {"/editMedical"})
public class EditMedical extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles the GET request to retrieve and display a specific medical record for editing.
     *
     * @param request  the HttpServletRequest object containing the request parameters
     * @param response the HttpServletResponse object for sending the response
     * @throws IOException if an I/O error occurs while processing the request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int medicalId = Integer.parseInt(request.getParameter("id"));
            GenericDAO<Medical> medicalDAO = new GenericDAO<>(Medical.class);
            Medical medical = medicalDAO.getById(medicalId);

            request.setAttribute("medical", medical);
            request.setAttribute("currentPage", "editMedical");
            request.getRequestDispatcher("add-medical.jsp").forward(request, response);
            logger.debug("Medical with ID {} loaded to edit", medicalId);

        } catch (Exception e) {
            logger.error("Error retrieving medical", e);
            response.sendRedirect("error.jsp");
        }
    }

    /**
     * Handles the HTTP POST request to update medical information.
     *
     * @param request  The HTTP request containing medical information to be updated.
     * @param response The HTTP response to be sent back after processing the request.
     * @throws IOException If an I/O error occurs while processing the request.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int medicalId = Integer.parseInt(request.getParameter("medicalId"));

            String medicationName = request.getParameter("name");
            String dateString = request.getParameter("date");
            LocalDate medicationDateGiven = LocalDate.parse(dateString);

            GenericDAO<Medical> medicalDAO = new GenericDAO<>(Medical.class);
            Medical medical = medicalDAO.getById(medicalId);

            medical.setMedicationName(medicationName);
            medical.setMedicationDateGiven(medicationDateGiven);

            medicalDAO.update(medical);
            logger.debug("Medical with ID {} updated", medicalId);
            response.sendRedirect("success.jsp");

        } catch (Exception e) {
            logger.error("Error editing medical", e);
            response.sendRedirect("error.jsp");
        }
    }
}
