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
import java.util.Properties;

@WebServlet(
        name = "addCatServlet",
        urlPatterns = {"/addCat"}
)
public class AddCat extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(AddCat.class);

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

            response.sendRedirect("index.jsp");

        } catch (Exception e) {
            logger.error("Error adding cat", e);
        }
    }
}
