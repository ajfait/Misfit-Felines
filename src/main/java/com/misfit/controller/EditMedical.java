package com.misfit.controller;

import com.misfit.entity.Cat;
import com.misfit.entity.Medical;
import com.misfit.persistence.GenericDAO;
import com.misfit.persistence.PropertiesLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(
        name = "editMedicalServlet",
        urlPatterns = {"/editMedical"}
)
public class EditMedical extends HttpServlet implements PropertiesLoader {
    GenericDAO medicalDAO = new GenericDAO(Medical.class);
    GenericDAO catDAO = new GenericDAO(Cat.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
