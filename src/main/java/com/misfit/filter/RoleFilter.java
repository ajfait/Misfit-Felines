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
 * The `RoleFilter` class in Java is a web filter that checks user permissions based on session
 * attributes before allowing access to certain paths in the web application.
 */
@WebFilter("/*")
public class RoleFilter implements Filter {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private RoleService roleService;

    /**
     * The init method initializes a RoleService object with a GenericDAO object for the Person class in a
     * Java filter.
     * 
     * @param filterConfig The `FilterConfig` parameter in the `init` method is typically used to provide
     * configuration information to a filter. It allows the filter to access initialization parameters
     * defined in the deployment descriptor (web.xml) or other configuration sources. This information can
     * be used by the filter to customize its behavior based on the
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        roleService = new RoleService(new GenericDAO<>(Person.class));
    }

    /**
     * This Java filter function checks user permissions based on session attributes before allowing
     * access to certain paths in a web application.
     * 
     * @param servletRequest The `servletRequest` parameter in the `doFilter` method represents the
     * request that a client sends to a web application. It contains information about the client's
     * request, such as the URL, parameters, headers, and other relevant data. In the provided code
     * snippet, the `servletRequest
     * @param servletResponse The `servletResponse` parameter in the `doFilter` method represents the
     * response that the servlet sends back to the client. It is of type `ServletResponse` which is the
     * superclass of `HttpServletResponse`. This object allows you to manipulate and control the
     * response that will be sent back to the client
     * @param filterChain The `filterChain` parameter in the `doFilter` method is an object that allows
     * the filter to pass the request and response to the next filter in the chain, or to the servlet
     * if the filter is the last one in the chain. By calling `filterChain.doFilter(request, response)
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String idToken = (String) request.getSession().getAttribute("idToken");
        Boolean isAdmin = (Boolean) request.getSession().getAttribute("isAdmin");

        // This block of code is responsible for checking the `idToken` stored in the session. If the
        // `idToken` is not null, it attempts to retrieve the `Person` object from the session. If the
        // `Person` object is not already stored in the session, it fetches the `Person` object using
        // the `idToken` through the `roleService.getPerson(idToken)` method.
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

        // This block of code in the `doFilter` method of the `RoleFilter` class is responsible for checking
        // the path of the incoming request and verifying the user permissions based on certain conditions
        // before allowing access to specific paths in the web application.
        if (path.contains("/addEvent") || path.contains("/editEvent") || path.contains("/deleteEvent") || path.contains("/addPerson") || path.contains("/deletePerson")) {
            if (isAdmin == null || !isAdmin) {
                response.sendRedirect("unauthorized.jsp");
                return;
            }
        }

        if (path.contains("/editCat") || path.contains("/deleteCat") || path.contains("/addMedical") || path.contains("/editMedical") || path.contains("/deleteMedical")) {
            if (isFoster == null || !isFoster) {
                response.sendRedirect("unauthorized.jsp");
                return;
            }
        }

        if (path.contains("/addCat") || path.contains("/viewPerson") || path.contains("/editPerson")) {
            if (person == null) {
                response.sendRedirect("unauthorized.jsp");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * The `destroy` method is overridden in a Java class without any implementation.
     */
    @Override
    public void destroy() {
        // Required but not needed
    }
}