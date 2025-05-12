package com.misfit.controller;

import com.misfit.entity.Event;
import com.misfit.persistence.*;
import org.apache.logging.log4j.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Servlet for deleting an event based on the event ID.
 * This servlet handles the POST request to delete an event.
 * It retrieves the event ID from the request parameters, deletes the event from the database,
 * and redirects to the viewEvent page upon successful deletion.
 * If an error occurs during the deletion process, it logs the error and redirects to the error.jsp page.
 */
@WebServlet(name = "deleteEventServlet", urlPatterns = {"/deleteEvent"})
public class DeleteEvent extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles the HTTP POST request to delete an event based on the provided event ID.
     *
     * @param request  The HTTP servlet request containing the event ID parameter
     * @param response The HTTP servlet response to redirect after deletion
     * @throws IOException If an input or output exception occurs
     */
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

            response.sendRedirect("viewEvent");

        } catch (Exception e) {
            logger.error("Error deleting event", e);
            response.sendRedirect("error.jsp");
        }
    }
}