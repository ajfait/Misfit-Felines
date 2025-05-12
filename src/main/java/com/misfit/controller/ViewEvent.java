package com.misfit.controller;

import com.misfit.entity.Event;
import com.misfit.persistence.*;
import org.apache.logging.log4j.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.*;
import java.util.*;

/**
 * This servlet handles the GET request to view a list of events.
 * It retrieves the list of events from the database using a GenericDAO,
 * sets the attributes for the JSP page, and forwards the request to view-events.jsp.
 * If an exception occurs during the process, it logs the error and redirects to an error page.
 *
 * @param request  the HttpServletRequest object
 * @param response the HttpServletResponse object
 * @throws IOException if an I/O error occurs
 */
@WebServlet(name = "viewEventServlet", urlPatterns = {"/viewEvent"})
public class ViewEvent extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles the HTTP GET request to display a list of events.
     *
     * This method retrieves a list of events from the database using a GenericDAO,
     * sets the list as an attribute in the request, sets the current page attribute,
     * and forwards the request to the view-events.jsp page to display the list of events.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            GenericDAO<Event> eventDAO = new GenericDAO<>(Event.class);
            List<Event> events = eventDAO.getAll();

            LocalDateTime now = LocalDateTime.now();
            events.removeIf(event -> event.getEventDateTimeStart().isBefore(now) || event.getEventDateTimeEnd().isBefore(now));

            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT).withLocale(Locale.US);

            for (Event event : events) {
                if (event.getEventDateTimeStart() != null) {
                    event.setStartFormatted(event.getEventDateTimeStart().format(formatter));
                }
                if (event.getEventDateTimeEnd() != null) {
                    event.setEndFormatted(event.getEventDateTimeEnd().format(formatter));
                }
            }

            request.setAttribute("eventList", events);
            request.setAttribute("currentPage", "viewEvent");
            request.getRequestDispatcher("view-events.jsp").forward(request, response);

        } catch (Exception e) {
            logger.error("Error retrieving event list", e);
            response.sendRedirect("error.jsp");
        }
    }
}
