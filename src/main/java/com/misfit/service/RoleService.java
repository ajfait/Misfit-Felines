package com.misfit.service;

import com.misfit.entity.Cat;
import com.misfit.entity.Person;
import com.misfit.persistence.GenericDAO;
import com.nimbusds.jwt.SignedJWT;
import org.apache.logging.log4j.*;

import java.util.List;

/**
 * The `RoleService` class provides methods to retrieve and manipulate `Person` and `Cat`
 * entities, handle user roles, and extract email information from ID tokens.
 */
public class RoleService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private final GenericDAO<Person> genericDAO;
    private final GenericDAO<Cat> catDao = new GenericDAO<>(Cat.class);

    /**
     * This is a constructor for the `RoleService` class in Java. It takes a parameter of type
     * `GenericDAO<Person>` and assigns it to the `genericDAO` field of the `RoleService` class. This
     * allows the `RoleService` class to work with a specific type of `GenericDAO` that deals with `Person`
     * entities.
     */
    public RoleService(GenericDAO<Person> genericDAO) {
        this.genericDAO = genericDAO;
    }

    /**
     * The function `getPerson` retrieves a `Person` object based on an ID token, handles exceptions and
     * logs errors.
     * 
     * @param idToken The `idToken` parameter is a token used to identify a user. It is passed to the
     * `getPerson` method to retrieve information about a person based on the email associated with the
     * token.
     * @return The `getPerson` method is returning a `Person` object if the email is successfully retrieved
     * from the token and a corresponding `Person` object is found in the database. If an exception occurs
     * during this process, the method catches the exception, logs an error, and returns `null`.
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

    /**
     * This Java function checks if a user is an admin based on their email extracted from an ID token.
     * 
     * @param idToken The `idToken` parameter is a token that represents the identity of a user. It is
     * typically used for authentication and authorization purposes in web applications.
     * @return The method `checkIfUserIsAdmin` returns a boolean value indicating whether the user
     * identified by the provided `idToken` is an admin or not.
     */
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

    /**
     * The function `isFoster` checks if a person is fostering any cats by retrieving a list of fostered
     * cats associated with the person and returning true if the list is not empty.
     * 
     * @param person A person object representing an individual who may be fostering cats.
     * @return The method `isFoster` returns a boolean value indicating whether the given `Person` is
     * fostering any cats. It checks if there are any fostered cats associated with the person and returns
     * `true` if there are, and `false` otherwise.
     */
    public boolean isFoster(Person person) {
        List<Cat> fosteredCats = catDao.getByFieldList("person", person);
        return fosteredCats != null && !fosteredCats.isEmpty();
    }

    /**
     * The function `getEmailFromToken` parses an ID token to extract and return the email claim, handling
     * exceptions by logging an error and returning the original token.
     * 
     * @param idToken The `idToken` parameter is a JSON Web Token (JWT) that contains information about the
     * user, such as their email address. The `getEmailFromToken` method parses this JWT and extracts the
     * email claim from it. If there is an error parsing the token, the method logs an error and
     * @return The method `getEmailFromToken` is returning the email claim extracted from the provided ID
     * token. If there is an error parsing the token, it will log an error message and return the original
     * ID token.
     */
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