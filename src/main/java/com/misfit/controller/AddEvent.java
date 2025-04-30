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
import java.time.format.DateTimeParseException;
import java.util.Set;

@WebServlet(
        name = "addEventServlet",
        urlPatterns = {"/addEvent"}
)
public class AddEvent extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

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
            LocalDateTime start = null;
            try {
                if (startString != null && !startString.isBlank()) {
                    start = LocalDateTime.parse(startString);
                }
            } catch (DateTimeParseException e) {
                logger.warn("Invalid event date format: {}", startString);
            }
            String endString = request.getParameter("end");
            LocalDateTime end = null;
            try {
                if (endString != null && !endString.isBlank()) {
                    end = LocalDateTime.parse(endString);
                }
            } catch (DateTimeParseException e) {
                logger.warn("Invalid event date format: {}", endString);
            }
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

            Set<ConstraintViolation<Event>> violations = ValidationUtil.validate(newEvent);

            if (!violations.isEmpty()) {
                request.setAttribute("violations", violations);
                request.setAttribute("event", newEvent);
                request.getRequestDispatcher("/WEB-INF/add-event.jsp").forward(request, response);
                return;
            }

            GenericDAO<Event> eventDAO = new GenericDAO<>(Event.class);
            eventDAO.insert(newEvent);
            logger.debug("event inserted");

            response.sendRedirect("success.jsp");

        } catch (Exception e) {
            logger.error("Error adding event", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
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
                }
            } catch (NumberFormatException e) {
                logger.error("Invalid eventId provided: " + eventIdParam, e);
            }
        }

        request.setAttribute("currentPage", "addEvent");

        request.getRequestDispatcher("/WEB-INF/add-event.jsp").forward(request, response);
    }
}
