package com.misfit.controller;

import com.misfit.entity.Medical;
import com.misfit.persistence.GenericDAO;
import com.misfit.persistence.PropertiesLoader;
import com.misfit.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

@WebServlet(
        name = "addMedicalServlet",
        urlPatterns = {"/addMedical"}
)
public class AddMedical extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.debug("doPost started");
            request.setCharacterEncoding("UTF-8");

            String name = request.getParameter("name");
            String dateString = request.getParameter("date");
            LocalDate date = LocalDate.parse(dateString);
            logger.debug("parameters received");

            Medical newMedical = new Medical();
            newMedical.setMedicationName(name);
            newMedical.setMedicationDateGiven(date);
            logger.debug("medical object created");

            Set<ConstraintViolation<Medical>> violations = ValidationUtil.validate(newMedical);

            if (!violations.isEmpty()) {
                request.setAttribute("violations", violations);
                request.setAttribute("medical", newMedical);
                request.getRequestDispatcher("/WEB-INF/add-medical.jsp").forward(request, response);
                return;
            }

            GenericDAO<Medical> medicalDAO = new GenericDAO<>(Medical.class);
            medicalDAO.insert(newMedical);
            logger.debug("medical inserted");

            response.sendRedirect("success.jsp");

        } catch (Exception e) {
            logger.error("Error adding medical", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");

        if (isAdmin == null || !isAdmin) {
            response.sendRedirect("unauthorized.jsp");
            return;
        }

        String medicalIdParam = request.getParameter("medicalId");
        if (medicalIdParam != null) {
            try {
                int medicalId = Integer.parseInt(medicalIdParam);
                GenericDAO<Medical> medicalDAO = new GenericDAO<>(Medical.class);
                Medical medical = medicalDAO.getById(medicalId);

                if (medical != null) {
                    request.setAttribute("medical", medical);
                }
            } catch (NumberFormatException e) {
                logger.error("Invalid medicalId provided: " + medicalIdParam, e);
            }
        }

        request.setAttribute("currentPage", "addMedical");

        request.getRequestDispatcher("/WEB-INF/add-medical.jsp").forward(request, response);
    }
}
