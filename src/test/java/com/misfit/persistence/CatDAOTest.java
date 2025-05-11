package com.misfit.persistence;

import com.misfit.entity.Cat;
import com.misfit.entity.Medical;
import com.misfit.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is used to test the CRUD methods in the DAO
 * for the Cat entity.
 * @author ajfait
 */
class CatDAOTest {
    GenericDAO<Cat> catDAO;
    GenericDAO<Person> personDAO;
    GenericDAO<Medical> medicalDAO;

    private final Logger logger = LogManager.getLogger(CatDAOTest.class);

    /**
     * This method instantiates a new CatDao object, gets
     * the database instance, and resets the data before
     * each test.
     */
    @BeforeEach
    void setUp() {
        logger.info("Test setup");
        catDAO = new GenericDAO<>(Cat.class);
        personDAO = new GenericDAO<>(Person.class);
        medicalDAO = new GenericDAO<>(Medical.class);
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    /**
     * This method tests inserting a new record into the database.
     * It creates a new cat, sets the values using the constructor,
     * and ensures the expected name is equal to the actual name
     * of the new cat.
     */
    @Test
    void insert() {
        Person person = personDAO.getById(1);
        Cat newCat = new Cat("Chester", "Male", LocalDate.of(2023, 5, 10), "Domestic Shorthair", "My most handsome.", false, person);
        catDAO.insert(newCat);
        Cat retrievedCat = catDAO.getById(4);
        assertNotNull(retrievedCat);
        assertEquals(newCat, retrievedCat);
    }

    /**
     * This method tests retrieving a cat by id.
     */
    @Test
    void getById() {
        Cat retrievedCat = catDAO.getById(2);
        assertNotNull(retrievedCat);
    }

    /**
     * This method tests getting all the records from the database.
     * It compares the size of the actual list to the expected
     * size of one record.
     */
    @Test
    void getAll() {
        List<Cat> allCats = catDAO.getAll();
        int expectedSize = 3;
        assertNotNull(allCats);
        assertEquals(expectedSize, allCats.size());
    }

    /**
     * This method tests updating a cat record. It gets the cat
     * from the database using id, changes the name, and then
     * compares the expected name to the actual name.
     */
    @Test
    void update() {
        String newCatName = "Molly";
        Cat updatedCat = catDAO.getById(3);
        updatedCat.setName(newCatName);
        catDAO.update(updatedCat);
        Cat retrievedCat = catDAO.getById(3);
        assertNotNull(retrievedCat);
        assertEquals(updatedCat, retrievedCat);
    }

    /**
     * This method tests deleting a record from the database.
     * It removes the record then checks to make sure the
     * record is null.
     */
    @Test
    void delete() {
        Cat catToDelete = catDAO.getById(2);
        catDAO.delete(catToDelete);
        assertEquals(null, catDAO.getById(2));
        List<Medical> meds = medicalDAO.getAll();
        Medical medToCheck = medicalDAO.getById(1);
        int expectedSize = 2;
        assertEquals(expectedSize, meds.size());
        assertEquals(null, medToCheck.getCat());
    }
}