package com.misfit.controller;

import com.misfit.entity.Medical;
import com.misfit.entity.Person;
import com.misfit.persistence.GenericDAO;
import com.misfit.persistence.PropertiesLoader;
import com.misfit.service.AdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            String date = request.getParameter("date");
            logger.debug("parameters received");

            Medical newMedical = new Medical();
            newMedical.setMedicationName(name);
            newMedical.setMedicationDateGiven(date);
            logger.debug("medical object created");

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
            throws IOException {
        String idToken = (String) request.getSession().getAttribute("idToken");
        logger.debug("Session ID: " + request.getSession().getId());
        logger.debug("ID Token from session: " + idToken);

        if (idToken == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        GenericDAO<Person> personDAO = new GenericDAO<>(Person.class);
        AdminService adminService = new AdminService(personDAO);

        try {
            boolean isAdmin = adminService.checkIfUserIsAdmin(idToken);

            if (!isAdmin) {
                response.sendRedirect("unauthorized.jsp");
                return;
            }

            request.setAttribute("isAdmin", true);
            request.getRequestDispatcher("/WEB-INF/add-medical.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error checking if user is admin", e);
            response.sendRedirect("unauthorized.jsp");
        }
    }
}
