package com.misfit.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Role filter. This filter is used to determine
 * servlet access based on user's role.
 */
@WebFilter("/*")
public class RoleFilter implements Filter {
    /**
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No init needed
    }

    /**
     * Provides admin access to add, edit, and delete event
     * and person records.
     * Provides foster access to edit, and delete cat records
     * for cats they foster.
     * Provides foster access to add, edit, and delete medical
     * records for cats they foster.
     * Provides user access to add cats.
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI();
        Boolean isAdmin = (Boolean) request.getSession().getAttribute("isAdmin");
        Boolean isFoster = (Boolean) request.getSession().getAttribute("isFoster");
        Object person = request.getSession().getAttribute("person");

        if (path.contains("/addEvent") || path.contains("/editEvent") || path.contains("/deleteEvent")
                || path.contains("/addPerson") || path.contains("/deletePerson")) {
            if (isAdmin == null || !isAdmin) {
                response.sendRedirect("unauthorized.jsp");
                return;
            }
        }

        if (path.contains("/editCat") || path.contains("/deleteCat")
                || path.contains("/addMedical") || path.contains("/editMedical") || path.contains("/deleteMedical")) {
            if (isFoster == null || !isFoster) {
                response.sendRedirect("unauthorized.jsp");
                return;
            }
        }

        if (path.contains("/addCat") 
                || path.contains("/viewPerson") || path.contains("/editPerson")) {
            if (person == null) {
                response.sendRedirect("unauthorized.jsp");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     *
     */
    @Override
    public void destroy() {
        // No cleanup needed
    }
}