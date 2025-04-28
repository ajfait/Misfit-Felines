package com.misfit.service;

import com.misfit.entity.Person;
import com.misfit.persistence.GenericDAO;
import com.nimbusds.jwt.SignedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Person service.
 */
public class PersonService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final GenericDAO<Person> genericDAO;

    /**
     * Instantiates a new Person service.
     *
     * @param genericDAO the generic dao
     */
    public PersonService(GenericDAO<Person> genericDAO) {
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