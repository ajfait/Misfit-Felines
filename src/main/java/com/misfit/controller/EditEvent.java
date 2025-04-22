package com.misfit.controller;

import com.misfit.entity.Event;
import com.misfit.persistence.GenericDAO;
import com.misfit.persistence.PropertiesLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "editEventServlet",
        urlPatterns = {"/editEvent"}
)
public class EditEvent extends HttpServlet implements PropertiesLoader {
    GenericDAO eventDAO = new GenericDAO(Event.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
