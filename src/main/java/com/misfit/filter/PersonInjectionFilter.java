package com.misfit.filter;

import com.misfit.entity.Person;
import com.misfit.persistence.GenericDAO;
import com.misfit.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * The type Person injection filter.
 */
@WebFilter("/*")
public class PersonInjectionFilter implements Filter {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private PersonService personService;

    @Override
    public void init(FilterConfig filterConfig) {
        personService = new PersonService(new GenericDAO<>(Person.class));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        String idToken = (String) req.getSession().getAttribute("idToken");

        if (idToken != null) {
            try {
                Person person = personService.getPerson(idToken);
                if (person != null) {
                    req.setAttribute("person", person);
                    logger.debug("Injected person into request: {}", person.getEmail());
                } else {
                    logger.debug("No person found for token.");
                }
            } catch (Exception e) {
                logger.error("Error injecting person into request", e);
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
