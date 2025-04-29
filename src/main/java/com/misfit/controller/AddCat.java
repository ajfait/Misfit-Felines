package com.misfit.controller;

import com.misfit.entity.Cat;
import com.misfit.persistence.GenericDAO;
import com.misfit.persistence.PropertiesLoader;
import com.misfit.service.CatBreedService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(
        name = "addCatServlet",
        urlPatterns = {"/addCat"}
)
public class AddCat extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.debug("doPost started");
            request.setCharacterEncoding("UTF-8");

            String name = request.getParameter("cat_name");
            String breed = request.getParameter("breed");
            String sex = request.getParameter("sex");
            String dob = request.getParameter("birthdate");
            String bio = request.getParameter("bio");
            boolean adoptable = request.getParameter("adoptable") != null;
            logger.debug("parameters received");

            Cat newCat = new Cat();
            newCat.setName(name);
            newCat.setBreed(breed);
            newCat.setSex(sex);
            newCat.setDob(dob);
            newCat.setBio(bio);
            newCat.setAdoptable(adoptable);
            logger.debug("cat object created");

            GenericDAO<Cat> catDAO = new GenericDAO<>(Cat.class);
            catDAO.insert(newCat);
            logger.debug("cat inserted");

            response.sendRedirect("success.jsp");

        } catch (Exception e) {
            logger.error("Error adding cat", e);
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

        try {
            CatBreedService breedService = new CatBreedService();
            List<String> breedNames = breedService.getBreedNames();
            request.setAttribute("breeds", breedNames);
        } catch (Exception e) {
            request.setAttribute("breeds", List.of("Unable to load breeds"));
            logger.error("Error loading cat breeds", e);
        }

        String catIdParam = request.getParameter("catId");
        if (catIdParam != null) {
            try {
                int catId = Integer.parseInt(catIdParam);
                GenericDAO<Cat> catDAO = new GenericDAO<>(Cat.class);
                Cat cat = catDAO.getById(catId);

                if (cat != null) {
                    request.setAttribute("cat", cat);
                }
            } catch (NumberFormatException e) {
                logger.error("Invalid catId provided: " + catIdParam, e);
            }
        }

        request.getRequestDispatcher("/WEB-INF/add-cat.jsp").forward(request, response);
    }
}
