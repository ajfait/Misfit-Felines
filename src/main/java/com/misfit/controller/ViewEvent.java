package com.misfit.controller;

import com.misfit.entity.Event;
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
        name = "viewEventServlet",
        urlPatterns = {"/viewEvent"}
)
public class ViewEvent extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            GenericDAO<Event> eventDAO = new GenericDAO<>(Event.class);
            List<Event> events = eventDAO.getAll();

            request.setAttribute("eventList", events);
            request.setAttribute("currentPage", "viewEvent");

            request.getRequestDispatcher("/WEB-INF/view-events.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error retrieving event list", e);
            response.sendRedirect("error.jsp");
        }
    }
}
