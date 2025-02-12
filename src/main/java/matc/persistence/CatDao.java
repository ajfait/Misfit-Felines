package matc.persistence;

import matc.entity.Cat;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

public class CatDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = edu.matc.persistence.SessionFactoryProvider.getSessionFactory();

    /**
     * Get cat by id
     */
    public Cat getById(int id) {
        Session session = sessionFactory.openSession();
        Cat cat = session.get(Cat.class, id);
        session.close();
        return cat;
    }

    /**
     * update cat
     * @param cat  cat to be updated
     */
    public void update(Cat cat) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(cat);
        transaction.commit();
        session.close();
    }

    /**
     * insert a new cat
     * @param cat  cat to be inserted
     */
    public int insert(Cat cat) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(cat);
        transaction.commit();
        id = cat.getCatId();
        session.close();
        return id;
    }

    /**
     * Delete a cat
     * @param cat cat to be deleted
     */
    public void delete(Cat cat) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(cat);
        transaction.commit();
        session.close();
    }

    /** Return a list of all cats
     *
     * @return All cats
     */
    public List<Cat> getAll() {

        Session session = sessionFactory.openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Cat> query = builder.createQuery(Cat.class);
        Root<Cat> root = query.from(Cat.class);
        List<Cat> cats = session.createSelectionQuery( query ).getResultList();

        logger.debug("The list of cats " + cats);
        session.close();

        return cats;
    }

    /**
     * Get cat by property (exact match)
     * sample usage: getByPropertyEqual("lastname", "Curry")
     */
    public List<Cat> getByPropertyEqual(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for cat with " + propertyName + " = " + value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Cat> query = builder.createQuery(Cat.class);
        Root<Cat> root = query.from(Cat.class);
        query.select(root).where(builder.equal(root.get(propertyName), value));
        List<Cat> cats = session.createSelectionQuery( query ).getResultList();

        session.close();
        return cats;
    }

    /**
     * Get cat by property (like)
     * sample usage: getByPropertyLike("lastname", "C")
     */
    public List<Cat> getByPropertyLike(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for cat with {} = {}",  propertyName, value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Cat> query = builder.createQuery(Cat.class);
        Root<Cat> root = query.from(Cat.class);
        Expression<String> propertyPath = root.get(propertyName);

        query.where(builder.like(propertyPath, "%" + value + "%"));

        List<Cat> cats = session.createQuery( query ).getResultList();
        session.close();
        return cats;
    }
}
