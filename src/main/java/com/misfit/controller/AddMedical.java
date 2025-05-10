package com.misfit.controller;

import com.misfit.email.SendEmailUsingOAuth;
import com.misfit.entity.*;
import com.misfit.persistence.*;
import com.misfit.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.apache.logging.log4j.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

/**
 * The type Add medical.
 */
@WebServlet(name = "addMedicalServlet", urlPatterns = {"/addMedical"})
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

            String catIdString = request.getParameter("catId");
            int catId = Integer.parseInt(catIdString);
            GenericDAO<Cat> catDAO = new GenericDAO<>(Cat.class);
            Cat cat = catDAO.getById(catId);

            Medical newMedical = new Medical();
            newMedical.setMedicationName(name);
            newMedical.setMedicationDateGiven(date);
            newMedical.setCat(cat);
            logger.debug("medical object created");

            Set<ConstraintViolation<Medical>> violations = ValidationUtil.validate(newMedical);

            if (!violations.isEmpty()) {
                request.setAttribute("violations", violations);
                request.setAttribute("medical", newMedical);
                request.getRequestDispatcher("add-medical.jsp").forward(request, response);
                return;
            }

            GenericDAO<Medical> medicalDAO = new GenericDAO<>(Medical.class);
            medicalDAO.insert(newMedical);
            logger.debug("medical inserted");

            /**
             try {
             String subject = "Medication Added for " + cat.getName();
             String body = "Hello,\n\nA new medication entry has been recorded in the Misfit Felines Foster Portal.\n"
             + "\n- Cat: " + cat.getName()
             + "\n- Medication: " + newMedical.getMedicationName()
             + "\n- Date Given: " + newMedical.getMedicationDateGiven()
             + "\n\nThank you,\nMisfit Felines Team";

             SendEmailUsingOAuth.sendEmail(subject, body);
             logger.debug("Email sent successfully.");
             } catch (Exception e) {
             logger.error("Error sending email", e);
             }
             */

            response.sendRedirect("success.jsp");

        } catch (Exception e) {
            logger.error("Error adding medical", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int catId = Integer.parseInt(request.getParameter("id"));
        GenericDAO<Cat> catDao = new GenericDAO<>(Cat.class);
        Cat cat = catDao.getById(catId);

        Person person = new Person();

        if (person == null) {
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
                response.sendRedirect("error.jsp");
                logger.error("Invalid medicalId provided: " + medicalIdParam, e);
            }
        }

        request.setAttribute("currentPage", "addMedical");

        request.getRequestDispatcher("add-medical.jsp").forward(request, response);
    }
}
