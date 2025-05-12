package com.misfit.controller;

import com.misfit.entity.Event;
import com.misfit.persistence.*;
import org.apache.logging.log4j.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

/**
 * Servlet for editing an event based on the provided ID.
 * This servlet handles GET requests to edit an event.
 *
 * @param request  the HTTP request containing the event ID to be edited
 * @param response the HTTP response to redirect to success or error page
 * @throws IOException if an input or output error occurs while the servlet is handling the request
 */
@WebServlet(name = "editEventServlet", urlPatterns = {"/editEvent"})
public class EditEvent extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles the HTTP GET request to load and display an event for editing.
     *
     * @param request  the HttpServletRequest object containing the request parameters
     * @param response the HttpServletResponse object for sending the response
     * @throws IOException if an I/O error occurs while processing the request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int eventId = Integer.parseInt(request.getParameter("id"));
            GenericDAO<Event> eventDAO = new GenericDAO<>(Event.class);
            Event event = eventDAO.getById(eventId);

            DateTimeFormatter htmlFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

            if (event.getEventDateTimeStart() != null) {
                request.setAttribute("startFormatted", event.getEventDateTimeStart().format(htmlFormat));
            }

            if (event.getEventDateTimeEnd() != null) {
                request.setAttribute("endFormatted", event.getEventDateTimeEnd().format(htmlFormat));
            }

            request.setAttribute("event", event);
            request.setAttribute("currentPage", "editEvent");
            request.getRequestDispatcher("add-event.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error loading event for edit", e);
            response.sendRedirect("error.jsp");
        }
    }

    /**
     * Handles the HTTP POST request to update an event with the provided details.
     *
     * @param request  The HTTP servlet request containing the event details
     * @param response The HTTP servlet response to redirect to success or error page
     * @throws IOException if an I/O error occurs while processing the request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));

            String eventName = request.getParameter("name");
            String eventLocationStreet = request.getParameter("street");
            String eventLocationCity = request.getParameter("city");
            String eventLocationState = request.getParameter("state");
            String eventLocationZip = request.getParameter("zip");

            String startString = request.getParameter("start");
            String endString = request.getParameter("end");

            DateTimeFormatter htmlDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime eventDateTimeStart = LocalDateTime.parse(startString, htmlDateTimeFormat);
            LocalDateTime eventDateTimeEnd = LocalDateTime.parse(endString, htmlDateTimeFormat);

            GenericDAO<Event> eventDAO = new GenericDAO<>(Event.class);
            Event event = eventDAO.getById(eventId);

            event.setEventName(eventName);
            event.setEventLocationStreet(eventLocationStreet);
            event.setEventLocationCity(eventLocationCity);
            event.setEventLocationState(eventLocationState);
            event.setEventLocationZip(eventLocationZip);
            event.setEventDateTimeStart(eventDateTimeStart);
            event.setEventDateTimeEnd(eventDateTimeEnd);

            eventDAO.update(event);
            logger.debug("Event with ID {} updated", eventId);
            response.sendRedirect("success.jsp");

        } catch (Exception e) {
            logger.error("Error editing event", e);
            response.sendRedirect("error.jsp");
        }
    }
}