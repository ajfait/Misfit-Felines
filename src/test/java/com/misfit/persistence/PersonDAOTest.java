package com.misfit.persistence;

import com.misfit.entity.Cat;
import com.misfit.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is used to test the CRUD methods in the DAO
 * for the Person entity.
 * @author ajfait
 */
class PersonDAOTest {
    GenericDAO<Person> personDAO;
    GenericDAO<Cat> catDAO;

    private final Logger logger = LogManager.getLogger(PersonDAOTest.class);

    /**
     * This method instantiates a new PersonDao object, gets
     * the database instance, and resets the data before
     * each test.
     */
    @BeforeEach
    void setUp() {
        logger.info("Test setup");
        personDAO = new GenericDAO<>(Person.class);
        catDAO = new GenericDAO<>(Cat.class);
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    /**
     * This method tests inserting a new record into the database.
     * It creates a new person, sets the values using the constructor,
     * and ensures the expected name is equal to the actual name
     * of the new person.
     */
    @Test
    void insert() {
        Person newPerson = new Person("John", "Doe", "608-222-1212", "jdoe@gmail.com", "Foster", "Adult Cats", false);
        personDAO.insert(newPerson);
        Person retrievedPerson = personDAO.getById(4);
        assertNotNull(retrievedPerson);
        assertEquals(newPerson.getFirstName(), retrievedPerson.getFirstName());
        assertEquals(newPerson.getLastName(), retrievedPerson.getLastName());
        assertEquals(newPerson.getPhone(), retrievedPerson.getPhone());
        assertEquals(newPerson.getEmail(), retrievedPerson.getEmail());
        assertEquals(newPerson.getRole(), retrievedPerson.getRole());
        assertEquals(newPerson.getPreferences(), retrievedPerson.getPreferences());
        assertEquals(newPerson.isAdmin(), retrievedPerson.isAdmin());
    }

    /**
     * This method tests retrieving a person by id.
     */
    @Test
    void getById() {
        Person retrievedPerson = personDAO.getById(1);
        assertNotNull(retrievedPerson);
    }

    /**
     * This method tests getting all the records from the database.
     * It compares the size of the actual list to the expected
     * size of one record.
     */
    @Test
    void getAll() {
        List<Person> allPeople = personDAO.getAll();
        int expectedSize = 3;
        assertNotNull(allPeople);
        assertEquals(expectedSize, allPeople.size());
    }

    /**
     * This method tests updating a person record. It gets the person
     * from the database using id, changes the name, and then
     * compares the expected name to the actual name.
     */
    @Test
    void update() {
        String newPersonName = "Ali";
        Person updatedPerson = personDAO.getById(1);
        updatedPerson.setFirstName(newPersonName);
        personDAO.update(updatedPerson);
        Person retrievedPerson = personDAO.getById(1);
        assertNotNull(retrievedPerson);
        assertEquals(updatedPerson.getFirstName(), retrievedPerson.getFirstName());
        assertEquals(updatedPerson.getLastName(), retrievedPerson.getLastName());
        assertEquals(updatedPerson.getPhone(), retrievedPerson.getPhone());
        assertEquals(updatedPerson.getEmail(), retrievedPerson.getEmail());
        assertEquals(updatedPerson.getRole(), retrievedPerson.getRole());
        assertEquals(updatedPerson.getPreferences(), retrievedPerson.getPreferences());
        assertEquals(updatedPerson.isAdmin(), retrievedPerson.isAdmin());
    }

    /**
     * This method tests deleting a record from the database.
     * It removes the record then checks to make sure the
     * record is null.
     */
    @Test
    void delete() {
        Person personToDelete = personDAO.getById(1);
        personDAO.delete(personToDelete);
        assertEquals(null, personDAO.getById(1));
        List<Cat> cats = catDAO.getAll();
        Cat catToCheck = catDAO.getById(1);
        int expectedSize = 3;
        assertEquals(expectedSize, cats.size());
        assertEquals(null, catToCheck.getPerson());
    }
}