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
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@WebServlet(
        name = "editEventServlet",
        urlPatterns = {"/editEvent"}
)
public class EditEvent extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

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
            request.getRequestDispatcher("/WEB-INF/add-event.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error loading event for edit", e);
            response.sendRedirect("error.jsp");
        }
    }

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
