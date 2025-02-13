package matc.persistence;

import matc.entity.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatDaoTest {

    CatDao catDao;

    @BeforeEach
    void setUp() {
        catDao = new CatDao();
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
    }

    @Test
    void getById() {
        Cat retrievedCat = catDao.getById(2);
        assertNotNull(retrievedCat);
        assertEquals("Maxine", retrievedCat.getName());
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