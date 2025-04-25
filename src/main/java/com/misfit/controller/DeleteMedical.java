package com.misfit.controller;

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

@WebServlet(
        name = "deleteMedicalServlet",
        urlPatterns = {"/deleteMedical"}
)
public class DeleteMedical extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(DeleteMedical.class);

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

            response.sendRedirect(medicalList);

        } catch (Exception e) {
            logger.error("Error deleting medical", e);
            response.sendRedirect("error.jsp");
        }
    }
}