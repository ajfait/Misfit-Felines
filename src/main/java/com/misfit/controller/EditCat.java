package com.misfit.controller;

import com.misfit.entity.Cat;
import com.misfit.persistence.GenericDAO;
import com.misfit.persistence.PropertiesLoader;
import com.misfit.service.CatBreedService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(
        name = "editCatServlet",
        urlPatterns = {"/editCat"}
)
public class EditCat extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int catId = Integer.parseInt(request.getParameter("catId"));
            GenericDAO<Cat> catDAO = new GenericDAO<>(Cat.class);
            Cat cat = catDAO.getById(catId);

            request.setAttribute("cat", cat);
            request.setAttribute("currentPage", "editCat");
            request.getRequestDispatcher("/WEB-INF/edit-cat.jsp").forward(request, response);
            logger.debug("Cat with ID {} loaded to edit", catId);

        } catch (Exception e) {
            logger.error("Error retrieving cat", e);
            response.sendRedirect("error.jsp");
        }

        try {
            CatBreedService breedService = new CatBreedService();
            List<String> breedNames = breedService.getBreedNames();
            request.setAttribute("breeds", breedNames);
        } catch (Exception e) {
            request.setAttribute("breeds", List.of("Unable to load breeds"));
            logger.error("Error loading cat breeds", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int catId = Integer.parseInt(request.getParameter("catId"));

            String name = request.getParameter("cat_name");
            String breed = request.getParameter("breed");
            String sex = request.getParameter("sex");
            String dobString = request.getParameter("birthdate");
            LocalDate dob = LocalDate.parse(dobString);
            boolean adoptable = "true".equals(request.getParameter("adoptable"));
            String bio = request.getParameter("bio");

            GenericDAO<Cat> catDAO = new GenericDAO<>(Cat.class);
            Cat cat = catDAO.getById(catId);

            cat.setName(name);
            cat.setBreed(breed);
            cat.setSex(sex);
            cat.setDob(dob);
            cat.setAdoptable(adoptable);
            cat.setBio(bio);

            catDAO.update(cat);
            logger.debug("Cat with ID {} updated", catId);
            response.sendRedirect("success.jsp");

        } catch (Exception e) {
            logger.error("Error editing cat", e);
            response.sendRedirect("error.jsp");
        }
    }
}
