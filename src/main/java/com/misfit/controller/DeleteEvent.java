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

@WebServlet(
        name = "deleteEventServlet",
        urlPatterns = {"/deleteEvent"}
)
public class DeleteEvent extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(DeleteEvent.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));

            GenericDAO<Event> eventDAO = new GenericDAO<>(Event.class);
            Event event = eventDAO.getById(eventId);

            if (event != null) {
                eventDAO.delete(event);
                logger.debug("Event with ID {} deleted", eventId);
            }

            response.sendRedirect(eventList);

        } catch (Exception e) {
            logger.error("Error deleting event", e);
            response.sendRedirect("error.jsp");
        }
    }
}