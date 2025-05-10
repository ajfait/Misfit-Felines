package com.misfit.controller;

import com.misfit.entity.Cat;
import com.misfit.entity.Medical;
import com.misfit.persistence.GenericDAO;
import com.misfit.persistence.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

/**
 * The type Edit medical.
 */
@WebServlet(name = "editMedicalServlet", urlPatterns = {"/editMedical"})
public class EditMedical extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

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
