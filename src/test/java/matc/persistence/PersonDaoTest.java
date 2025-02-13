package matc.persistence;

import matc.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PersonDaoTest {

    PersonDao personDao;

    @BeforeEach
    void setUp() {
        personDao = new PersonDao();
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    @Test
    void getById() {
        Person retrievedPerson = personDao.getById(1);
        assertNotNull(retrievedPerson);
        assertEquals("Alison", retrievedPerson.getFirstName());
    }

    @Test
    void update() {
    }

    @Test
    void insert() {
    }

    @Test
    void delete() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getByPropertyEqual() {
    }

    @Test
    void getByPropertyLike() {
    }
}
