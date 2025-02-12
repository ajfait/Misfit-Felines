package matc.persistence;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import matc.entity.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

public class PersonDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = edu.matc.persistence.SessionFactoryProvider.getSessionFactory();

    /**
     * Get person by id
     */
    public Person getById(int id) {
        Session session = sessionFactory.openSession();
        Person person = session.get(Person.class, id);
        session.close();
        return person;
    }

    /**
     * update person
     * @param person  person to be updated
     */
    public void update(Person person) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(person);
        transaction.commit();
        session.close();
    }

    /**
     * insert a new person
     * @param person  person to be inserted
     */
    public int insert(Person person) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(person);
        transaction.commit();
        id = person.getId();
        session.close();
        return id;
    }

    /**
     * Delete a person
     * @param person person to be deleted
     */
    public void delete(Person person) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(person);
        transaction.commit();
        session.close();
    }

    /** Return a list of all people
     *
     * @return All people
     */
    public List<Person> getAll() {

        Session session = sessionFactory.openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Person> query = builder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);
        List<Person> people = session.createSelectionQuery( query ).getResultList();

        logger.debug("The list of people " + people);
        session.close();

        return people;
    }

    /**
     * Get person by property (exact match)
     * sample usage: getByPropertyEqual("lastname", "Curry")
     */
    public List<Person> getByPropertyEqual(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for person with " + propertyName + " = " + value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Person> query = builder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);
        query.select(root).where(builder.equal(root.get(propertyName), value));
        List<Person> people = session.createSelectionQuery( query ).getResultList();

        session.close();
        return people;
    }

    /**
     * Get person by property (like)
     * sample usage: getByPropertyLike("lastname", "C")
     */
    public List<Person> getByPropertyLike(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for person with {} = {}",  propertyName, value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Person> query = builder.createQuery(Person.class);
        Root<Person> root = query.from(Person.class);
        Expression<String> propertyPath = root.get(propertyName);

        query.where(builder.like(propertyPath, "%" + value + "%"));

        List<Person> people = session.createQuery( query ).getResultList();
        session.close();
        return people;
    }
}

