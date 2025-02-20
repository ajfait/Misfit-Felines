package matc.persistence;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import matc.entity.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

/**
 * The type Event dao.
 */
public class EventDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    /**
     * The Session factory.
     */
    SessionFactory sessionFactory = matc.persistence.SessionFactoryProvider.getSessionFactory();

    /**
     * Gets by event id.
     *
     * @param id the id
     * @return the by event id
     */
    public Event getByEventId(int id) {
        Session session = sessionFactory.openSession();
        Event event = session.get(Event.class, id);
        session.close();
        return event;
    }

    /**
     * Update.
     *
     * @param event the event
     */
    public void update(Event event) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(event);
        transaction.commit();
        session.close();
    }

    /**
     * Insert int.
     *
     * @param event the event
     * @return the int
     */
    public int insert(Event event) {
        int e_id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(event);
        transaction.commit();
        e_id = event.getEventId();
        session.close();
        return e_id;
    }

    /**
     * Delete.
     *
     * @param event the event
     */
    public void delete(Event event) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(event);
        transaction.commit();
        session.close();
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    public List<Event> getAll() {

        Session session = sessionFactory.openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);
        List<Event> events = session.createSelectionQuery( query ).getResultList();

        logger.debug("The list of events " + events);
        session.close();

        return events;
    }

    /**
     * Gets by property equal.
     *
     * @param propertyName the property name
     * @param value        the value
     * @return the by property equal
     */
    public List<Event> getByPropertyEqual(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for event with " + propertyName + " = " + value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);
        query.select(root).where(builder.equal(root.get(propertyName), value));
        List<Event> events = session.createSelectionQuery( query ).getResultList();

        session.close();
        return events;
    }

    /**
     * Gets by property like.
     *
     * @param propertyName the property name
     * @param value        the value
     * @return the by property like
     */
    public List<Event> getByPropertyLike(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for event with {} = {}",  propertyName, value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);
        Expression<String> propertyPath = root.get(propertyName);

        query.where(builder.like(propertyPath, "%" + value + "%"));

        List<Event> events = session.createQuery( query ).getResultList();
        session.close();
        return events;
    }
}
