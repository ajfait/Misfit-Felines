package com.misfit.filter;

import com.misfit.entity.Person;
import com.misfit.persistence.GenericDAO;
import com.misfit.service.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private final Logger logger = LogManager.getLogger(this.getClass());
    private RoleService roleService;
    
    /**
     * @param filterConfig 
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        roleService = new RoleService(new GenericDAO<>(Person.class));
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

        String idToken = (String) request.getSession().getAttribute("idToken");

        Boolean isAdmin = (Boolean) request.getSession().getAttribute("isAdmin");
        if (idToken != null) {
            try {
                Person person = (Person) request.getSession().getAttribute("person");

                if (person == null) {
                    person = roleService.getPerson(idToken);
                    if (person != null) {
                        boolean isFoster = roleService.isFoster(person);
                        request.getSession().setAttribute("isFoster", isFoster);
                        request.getSession().setAttribute("personId", person.getPersonId());
                        request.getSession().setAttribute("person", person);

                        request.setAttribute("isFoster", isFoster);
                        request.setAttribute("personId", person.getPersonId());
                    }
                }

                if (person != null) {
                    request.setAttribute("person", person);
                    logger.debug("Injected person into request: {}", person.getEmail());

                    if (isAdmin == null) {
                        isAdmin = roleService.checkIfUserIsAdmin(idToken);
                        request.getSession().setAttribute("isAdmin", isAdmin);
                    }
                    request.setAttribute("isAdmin", isAdmin);
                    logger.debug("Admin status: {}", isAdmin);
                }

            } catch (Exception e) {
                logger.error("Error injecting person into request", e);
            }
        }

        String path = request.getRequestURI();
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

    }
}