package com.misfit.persistence;

import com.misfit.entity.Person;

/**
 * The type Person dao.
 */
public class PersonDAO extends GenericDAO<Person> {
    /**
     * Instantiates a new Person dao.
     */
    public PersonDAO() {
        super(Person.class);
    }
}