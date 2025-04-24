package com.misfit.service;

import com.misfit.entity.Person;
import com.misfit.persistence.GenericDAO;
import com.nimbusds.jwt.SignedJWT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdminService {
    private final Logger logger = LogManager.getLogger(AdminService.class);
    private final GenericDAO<Person> genericDAO;

    public AdminService(GenericDAO<Person> genericDAO) {
        this.genericDAO = genericDAO;
    }

    public boolean checkIfUserIsAdmin(String idToken) throws Exception {
        String email = getEmailFromToken(idToken);
        Person person = genericDAO.getByField("email", email);
        return person != null && person.isAdmin();
    }

    private String getEmailFromToken(String idToken) throws Exception {
        SignedJWT jwt = SignedJWT.parse(idToken);
        return (String) jwt.getJWTClaimsSet().getClaim("email");
    }
}