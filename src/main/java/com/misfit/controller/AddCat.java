package com.misfit.controller;

import com.misfit.entity.Cat;
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
import java.util.Properties;

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
            request.getRequestDispatcher("/WEB-INF/add-cat.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error checking if user is admin", e);
            response.sendRedirect("unauthorized.jsp");
        }
    }
}
