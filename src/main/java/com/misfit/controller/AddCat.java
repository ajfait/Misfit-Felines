package com.misfit.controller;

import com.misfit.entity.Cat;
import com.misfit.entity.Person;
import com.misfit.persistence.GenericDAO;
import com.misfit.persistence.PropertiesLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "addCatServlet",
        urlPatterns = {"/addCat"}
)
public class AddCat extends HttpServlet implements PropertiesLoader {
    GenericDAO catDAO = new GenericDAO(Cat.class);
    GenericDAO personDAO = new GenericDAO(Person.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
