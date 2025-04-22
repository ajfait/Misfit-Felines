package com.misfit.controller;

import com.misfit.entity.Person;
import com.misfit.persistence.GenericDAO;
import com.misfit.persistence.PropertiesLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "editPersonServlet",
        urlPatterns = {"/editPerson"}
)
public class EditPerson extends HttpServlet implements PropertiesLoader {
    GenericDAO personDAO = new GenericDAO(Person.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
