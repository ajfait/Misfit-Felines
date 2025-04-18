package matc.persistence;

import matc.entity.Cat;
import matc.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is used to test the CRUD methods in the DAO.
 * @author ajfait
 */
class CatDaoTest {
    // Initializes CatDao object.
    CatDao catDao;

    //
    private final Logger logger = LogManager.getLogger(CatDaoTest.class);

    /**
     * This method instantiates a new CatDao object, gets
     * the database instance, and resets the data before
     * each test.
     */
    @BeforeEach
    void setUp() {
        logger.info("Test setup");
        catDao = new CatDao();
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    /**
     * This method tests retrieving a cat by id.
     */
    @Test
    void getById() {
        Cat retrievedCat = catDao.getById(2);
        assertNotNull(retrievedCat);
    }

    /**
     * This method tests updating a cat record. It gets the cat
     * from the database using id, changes the name, and then
     * compares the expected name to the actual name.
     */
    @Test
    void update() {
        String newCatName = "Molly";
        Cat updatedCat = catDao.getById(2);
        updatedCat.setName(newCatName);
        catDao.update(updatedCat);
        Cat retrievedCat = catDao.getById(2);
        assertNotNull(retrievedCat);
        assertEquals(updatedCat, retrievedCat);
    }

    /**
     * This method tests inserting a new record into the database.
     * It creates a new cat, sets the values using the constructor,
     * and ensures the expected name is equal to the actual name
     * of the new cat.
     */
    @Test
    void insert() {
        PersonDao personDao = new PersonDao();
        Person person = personDao.getById(1);
        Cat newCat = new Cat("Chester", "M", "2000-01-01", "DSH", "My most handsome.", false, person);
        catDao.insert(newCat);
        Cat retrievedCat = catDao.getById(3);
        assertNotNull(retrievedCat);
        assertEquals(newCat, retrievedCat);
    }

    /**
     * This method tests deleting a record from the database.
     * It removes the record then checks to make sure the
     * record is null.
     */
    @Test
    void delete() {
        Cat catToDelete = catDao.getById(2);
        catDao.delete(catToDelete);
        assertEquals(null, catDao.getById(2));
    }

    /**
     * This method tests getting all the records from the database.
     * It compares the size of the actual list to the expected
     * size of one record.
     */
    @Test
    void getAll() {
        List<Cat> allCats = catDao.getAll();
        int expectedSize = 1;
        assertNotNull(allCats);
        assertEquals(expectedSize, allCats.size());
    }

    /**
     * This method tests getting a record from the database
     * whose value equals a specific query. In this case, it
     * retrieves all the cats with the name Maxine and
     * compares the actual list size to the expected list
     * size of 1.
     */
    @Test
    void getByPropertyEqual() {
        List<Cat> specificCat = catDao.getByPropertyEqual("name", "Maxine");
        Cat expectedCat = catDao.getById(2);
        assertNotNull(specificCat);
        assertEquals(expectedCat, specificCat.get(0));
    }

    /**
     * This method tests getting a record from the database
     * whose value is like a specific query. In this case, it
     * retrieves all the cats with Max in the name and
     * compares the actual list size to the expected list
     * size of 1.
     */
    @Test
    void getByPropertyLike() {
        List<Cat> likeCat = catDao.getByPropertyLike("name", "Max");
        Cat expectedCat = catDao.getById(2);
        assertNotNull(likeCat);
        assertEquals(expectedCat, likeCat.get(0));
    }
}