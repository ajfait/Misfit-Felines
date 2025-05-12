package com.misfit.persistence;

import com.misfit.entity.*;
import org.junit.jupiter.api.*;
import org.apache.logging.log4j.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is used to test the CRUD methods in the DAO
 * for the Medical entity.
 *
 * @author ajfait
 */
class MedicalDAOTest {
    GenericDAO<Medical> medicalDAO;
    GenericDAO<Cat> catDAO;

    private final Logger logger = LogManager.getLogger(MedicalDAOTest.class);

    /**
     * This method instantiates a new MedicalDao object, gets
     * the database instance, and resets the data before
     * each test.
     */
    @BeforeEach
    void setUp() {
        logger.info("Test setup");
        medicalDAO = new GenericDAO<>(Medical.class);
        catDAO = new GenericDAO<>(Cat.class);
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    /**
     * This method tests inserting a new record into the database.
     * It creates a new medical, sets the values using the constructor,
     * and ensures the expected name is equal to the actual name
     * of the new medical.
     */
    @Test
    void insert() {
        Cat cat = catDAO.getById(2);
        Medical newMedical = new Medical("Doxycycline", LocalDate.of(2025, 4, 19), cat);
        medicalDAO.insert(newMedical);
        Medical retrievedMedical = medicalDAO.getById(3);
        assertNotNull(retrievedMedical);
        assertEquals(newMedical.getMedicationName(), retrievedMedical.getMedicationName());
    }

    /**
     * This method tests retrieving a medical by id.
     */
    @Test
    void getById() {
        Medical retrievedMedical = medicalDAO.getById(1);
        assertNotNull(retrievedMedical);
    }

    /**
     * This method tests getting all the records from the database.
     * It compares the size of the actual list to the expected
     * size of two records.
     */
    @Test
    void getAll() {
        List<Medical> allMedical = medicalDAO.getAll();
        int expectedSize = 2;
        assertNotNull(allMedical);
        assertEquals(expectedSize, allMedical.size());
    }

    /**
     * This method tests updating a medical record. It gets the medical
     * from the database using id, changes the name, and then
     * compares the expected name to the actual name.
     */
    @Test
    void update() {
        String newMedicalName = "Doxycycline";
        Medical updatedMedical = medicalDAO.getById(2);
        updatedMedical.setMedicationName(newMedicalName);
        medicalDAO.update(updatedMedical);
        Medical retrievedMedical = medicalDAO.getById(2);
        assertNotNull(retrievedMedical);
        assertEquals(updatedMedical.getMedicationName(), retrievedMedical.getMedicationName());
    }

    /**
     * This method tests deleting a record from the database.
     * It removes the record then checks to make sure the
     * record is null.
     */
    @Test
    void delete() {
        Medical medicalToDelete = medicalDAO.getById(1);
        medicalDAO.delete(medicalToDelete);
        assertEquals(null, medicalDAO.getById(1));
    }
}