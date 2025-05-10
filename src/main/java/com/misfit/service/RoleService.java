package com.misfit.service;

import com.misfit.entity.Cat;
import com.misfit.entity.Person;
import com.misfit.persistence.GenericDAO;
import com.nimbusds.jwt.SignedJWT;
import org.apache.logging.log4j.*;

import java.util.List;

public class RoleService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final GenericDAO<Person> genericDAO;
    private final GenericDAO<Cat> catDao = new GenericDAO<>(Cat.class);

    public RoleService(GenericDAO<Person> genericDAO) {
        this.genericDAO = genericDAO;
    }

    /**
     * Gets person.
     *
     * @param idToken the id token
     * @return the person
     * @throws Exception the exception
     */
    public Person getPerson(String idToken) throws Exception {
        try {
            String email = getEmailFromToken(idToken);
            Person person = genericDAO.getByField("email", email);
            logger.debug("Email from token: {}", email);
            return person;
        } catch (Exception e) {
            logger.error("Error checking if user is admin", e);
            return null;
        }
    }

    public boolean checkIfUserIsAdmin(String idToken) throws Exception {
        try {
            String email = getEmailFromToken(idToken);
            Person person = genericDAO.getByField("email", email);
            logger.debug("Email from token: {}", email);
            return person != null && person.isAdmin();
        } catch (Exception e) {
            logger.error("Error checking if user is admin", e);
            return false;
        }
    }

    public boolean isFoster(Person person) {
        List<Cat> fosteredCats = catDao.getByFieldList("person", person);
        return fosteredCats != null && !fosteredCats.isEmpty();
    }

    private String getEmailFromToken(String idToken) throws Exception {
        try {
            SignedJWT jwt = SignedJWT.parse(idToken);
            return (String) jwt.getJWTClaimsSet().getClaim("email");
        } catch (Exception e) {
            logger.error("Error parsing id token");
            return idToken;
        }
    }
}
