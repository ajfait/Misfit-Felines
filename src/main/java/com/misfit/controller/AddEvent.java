package com.misfit.controller;

import com.misfit.entity.Event;
import com.misfit.persistence.GenericDAO;
import com.misfit.persistence.PropertiesLoader;
import com.misfit.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Set;

/**
 * The type Add event.
 */
@WebServlet(name = "addEventServlet", urlPatterns = {"/addEvent"})
public class AddEvent extends HttpServlet implements PropertiesLoader {
    private static final Logger logger = LogManager.getLogger(AddEvent.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            logger.debug("doPost started");
            request.setCharacterEncoding("UTF-8");

            String name = request.getParameter("name");
            String street = request.getParameter("street");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String zip = request.getParameter("zip");
            String startString = request.getParameter("start");
            String endString = request.getParameter("end");

            LocalDateTime start = parseDateTime(startString, DATE_TIME_FORMATTER);
            LocalDateTime end = parseDateTime(endString, DATE_TIME_FORMATTER);

            logger.debug("parameters received");

            Event newEvent = new Event();
            newEvent.setEventName(name);
            newEvent.setEventLocationStreet(street);
            newEvent.setEventLocationCity(city);
            newEvent.setEventLocationState(state);
            newEvent.setEventLocationZip(zip);
            newEvent.setEventDateTimeStart(start);
            newEvent.setEventDateTimeEnd(end);

            logger.debug("event object created");

            if (start != null) {
                request.setAttribute("startFormatted", start.format(DATE_TIME_FORMATTER));
            }
            if (end != null) {
                request.setAttribute("endFormatted", end.format(DATE_TIME_FORMATTER));
            }

            Set<ConstraintViolation<Event>> violations = ValidationUtil.validate(newEvent);

            if (!violations.isEmpty()) {
                request.setAttribute("violations", violations);
                request.setAttribute("event", newEvent);
                request.getRequestDispatcher("add-event.jsp").forward(request, response);
                return;
            }

            // Insert Event into the database
            GenericDAO<Event> eventDAO = new GenericDAO<>(Event.class);
            eventDAO.insert(newEvent);
            logger.debug("event inserted");

            response.sendRedirect("success.jsp");

        } catch (Exception e) {
            logger.error("Error adding event", e);
            try {
                response.sendRedirect("error.jsp");
            } catch (IOException ioException) {
                logger.error("Redirect to error.jsp failed", ioException);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Boolean isAdmin = (Boolean) request.getAttribute("isAdmin");

        if (isAdmin == null || !isAdmin) {
            response.sendRedirect("unauthorized.jsp");
            return;
        }

        String eventIdParam = request.getParameter("eventId");
        if (eventIdParam != null) {
            try {
                int eventId = Integer.parseInt(eventIdParam);
                GenericDAO<Event> eventDAO = new GenericDAO<>(Event.class);
                Event event = eventDAO.getById(eventId);

                if (event != null) {
                    request.setAttribute("event", event);
                    if (event.getEventDateTimeStart() != null) {
                        request.setAttribute("startFormatted", event.getEventDateTimeStart().format(DATE_TIME_FORMATTER));
                    }
                    if (event.getEventDateTimeEnd() != null) {
                        request.setAttribute("endFormatted", event.getEventDateTimeEnd().format(DATE_TIME_FORMATTER));
                    }
                }
            } catch (NumberFormatException e) {
                logger.error("Invalid eventId provided: " + eventIdParam, e);
            }
        }

        request.setAttribute("currentPage", "addEvent");
        request.getRequestDispatcher("add-event.jsp").forward(request, response);
    }

    private LocalDateTime parseDateTime(String dateTimeString, DateTimeFormatter formatter) {
        try {
            if (dateTimeString != null && !dateTimeString.isBlank()) {
                return LocalDateTime.parse(dateTimeString, formatter);
            }
        } catch (DateTimeParseException e) {
            logger.warn("Invalid event date format: {}", dateTimeString);
        }
        return null;
    }
}