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

@WebServlet(
        name = "deleteCatServlet",
        urlPatterns = {"/deleteCat"}
)
public class DeleteCat extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(DeleteCat.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int catId = Integer.parseInt(request.getParameter("catId"));

            GenericDAO<Cat> catDAO = new GenericDAO<>(Cat.class);
            Cat cat = catDAO.getById(catId);

            if (cat != null) {
                catDAO.delete(cat);
                logger.debug("Cat with ID {} deleted", catId);
            }

            response.sendRedirect(catList);

        } catch (Exception e) {
            logger.error("Error deleting cat", e);
            response.sendRedirect("error.jsp");
        }
    }
}