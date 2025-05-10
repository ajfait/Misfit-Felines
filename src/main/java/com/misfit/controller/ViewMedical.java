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
import java.util.List;

/**
 * The type View medical.
 */
@WebServlet(name = "viewMedicalServlet", urlPatterns = {"/viewMedical"})
public class ViewMedical extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

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
