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
import java.util.List;

@WebServlet(
        name = "viewMedicalServlet",
        urlPatterns = {"/viewMedical"}
)
public class ViewMedical extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            GenericDAO<Medical> medicalDAO = new GenericDAO<>(Medical.class);
            List<Medical> medicals = medicalDAO.getAll();

            request.setAttribute("medicalList", medicals);

            request.getRequestDispatcher("/WEB-INF/view-medical.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error retrieving medical list", e);
            response.sendRedirect("error.jsp");
        }
    }
}
