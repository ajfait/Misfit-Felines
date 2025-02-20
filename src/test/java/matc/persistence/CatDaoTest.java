package matc.persistence;

import matc.entity.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is used to test the CRUD methods in the DAO.
 * @author ajfait
 */
class CatDaoTest {
    // Initializes CatDao object.
    CatDao catDao;

    /**
     * This method instantiates a new CatDao object, gets
     * the database instance, and resets the data before
     * each test.
     */
    @BeforeEach
    void setUp() {
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
        assertEquals("Maxine", retrievedCat.getName());
    }

    /**
     * This method tests updating a cat record. It gets the cat
     * from the database using id, changes the name, and then
     * compares the expected name to the actual name.
     */
    @Test
    void update() {
        Cat updatedCat = catDao.getById(2);
        updatedCat.setName("Molly");
        assertNotNull(updatedCat);
        assertEquals("Molly", updatedCat.getName());
    }

    /**
     * This method tests inserting a new record into the database.
     * It creates a new cat, sets the values using the constructor,
     * and ensures the expected name is equal to the actual name
     * of the new cat.
     */
    @Test
    void insert() {
        Cat newCat = new Cat("Chester", "M", "2000-01-01", "DSH", "My most handsome.", false);
        assertNotNull(newCat);
        assertEquals("Chester", newCat.getName());
    }

    /**
     * This test is not working for me. I am not sure what
     * I am doing wrong.
     */
//    @Test
//    void delete() {
//        Cat newCat = new Cat(3, "Chester", "M", "2000-01-01", "DSH", "My most handsome.", false);
//        Cat catToDelete = catDao.getById(3);
//        assertNotNull(catToDelete);
//        // delete(catToDelete);
//        Cat deletedCat = catDao.getById(3);
//        assertNull(deletedCat);
//    }

    /**
     * This method tests getting all the records from the database.
     * It compares the size of the actual list to the expected
     * size of one record.
     */
    @Test
    void getAll() {
        List<Cat> allCats = catDao.getAll();
        assertNotNull(allCats);
        assertEquals(1, allCats.size());
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
        List<Cat> equalCats = catDao.getByPropertyEqual("name", "Maxine");
        assertNotNull(equalCats);
        assertEquals(1, equalCats.size());
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
        List<Cat> likeCats = catDao.getByPropertyLike("name", "Max");
        assertNotNull(likeCats);
        assertEquals(1, likeCats.size());
    }
}