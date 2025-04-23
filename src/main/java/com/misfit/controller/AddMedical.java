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

@WebServlet(
        name = "addMedicalServlet",
        urlPatterns = {"/addMedical"}
)
public class AddMedical extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(AddMedical.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.debug("doPost started");
            request.setCharacterEncoding("UTF-8");

            String name = request.getParameter("name");
            String date = request.getParameter("date");
            logger.debug("parameters received");

            Medical newMedical = new Medical();
            newMedical.setMedicationName(name);
            newMedical.setMedicationDateGiven(date);
            logger.debug("medical object created");

            GenericDAO<Medical> medicalDAO = new GenericDAO<>(Medical.class);
            medicalDAO.insert(newMedical);
            logger.debug("medical inserted");

            response.sendRedirect("index.jsp");

        } catch (Exception e) {
            logger.error("Error adding medical", e);
        }
    }
}
