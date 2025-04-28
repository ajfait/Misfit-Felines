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
        name = "editEventServlet",
        urlPatterns = {"/editEvent"}
)
public class EditEvent extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int eventId = Integer.parseInt(request.getParameter("eventId"));
            GenericDAO<Event> eventDAO = new GenericDAO<>(Event.class);
            Event event = eventDAO.getById(eventId);

            request.setAttribute("event", event);
            request.getRequestDispatcher("/WEB-INF/add-event.jsp");

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
            String eventDateTimeStart = request.getParameter("start");
            String eventDateTimeEnd = request.getParameter("end");

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
