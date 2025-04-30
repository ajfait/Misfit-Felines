package com.misfit.controller;

import com.misfit.entity.Cat;
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
        name = "viewCatServlet",
        urlPatterns = {"/viewCat"}
)
public class ViewCat extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            GenericDAO<Cat> catDAO = new GenericDAO<>(Cat.class);
            List<Cat> cats = catDAO.getAll();

            request.setAttribute("catList", cats);
            request.setAttribute("currentPage", "viewCat");

            request.getRequestDispatcher("/WEB-INF/view-cats.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error retrieving cat list", e);
            response.sendRedirect("error.jsp");
        }
    }
}
