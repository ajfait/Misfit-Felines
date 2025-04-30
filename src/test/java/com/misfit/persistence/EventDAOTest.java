package com.misfit.persistence;

import com.misfit.entity.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is used to test the CRUD methods in the DAO
 * for the Event entity.
 *
 * @author ajfait
 */
class EventDAOTest {
    GenericDAO<Event> eventDAO;

    private final Logger logger = LogManager.getLogger(EventDAOTest.class);

    /**
     * This method instantiates a new EventDao object, gets
     * the database instance, and resets the data before
     * each test.
     */
    @BeforeEach
    void setUp() {
        logger.info("Test setup");
        eventDAO = new GenericDAO<>(Event.class);
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    /**
     * This method tests inserting a new record into the database.
     * It creates a new event, sets the values using the constructor,
     * and ensures the expected name is equal to the actual name
     * of the new event.
     */
    @Test
    void insert() {
        Event newEvent = new Event
                ("Adoption Fair at Tabby & Jack's Pet Supplies and Grooming Stoughton",
                        "2392 Jackson St", "Stoughton",
                        "WI",
                        "53589",
                        LocalDateTime.of(2025, 7, 19, 11, 0),
                        LocalDateTime.of(2025, 7, 19, 14, 0));
        eventDAO.insert(newEvent);
        Event retrievedEvent = eventDAO.getById(2);
        assertNotNull(retrievedEvent);
        assertEquals(newEvent.getEventName(), retrievedEvent.getEventName());
    }

    /**
     * This method tests retrieving an event by id.
     */
    @Test
    void getById() {
        Event retrievedEvent = eventDAO.getById(1);
        assertNotNull(retrievedEvent);
    }

    /**
     * This method tests getting all the records from the database.
     * It compares the size of the actual list to the expected
     * size of one record.
     */
    @Test
    void getAll() {
        List<Event> allEvents = eventDAO.getAll();
        int expectedSize = 1;
        assertNotNull(allEvents);
        assertEquals(expectedSize, allEvents.size());
    }

    /**
     * This method tests updating an event record. It gets the event
     * from the database using id, changes the name, and then
     * compares the expected name to the actual name.
     */
    @Test
    void update() {
        String newEventName = "Adoption Fair at PetSmart Janesville";
        Event updatedEvent = eventDAO.getById(1);
        updatedEvent.setEventName(newEventName);
        eventDAO.update(updatedEvent);
        Event retrievedEvent = eventDAO.getById(1);
        assertNotNull(retrievedEvent);
        assertEquals(updatedEvent.getEventName(), retrievedEvent.getEventName());
    }

    /**
     * This method tests deleting a record from the database.
     * It removes the record then checks to make sure the
     * record is null.
     */
    @Test
    void delete() {
        Event eventToDelete = eventDAO.getById(1);
        eventDAO.delete(eventToDelete);
        assertEquals(null, eventDAO.getById(1));
    }
}