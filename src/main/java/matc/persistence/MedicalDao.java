package matc.persistence;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import matc.entity.Medical;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

/**
 * The type Medical dao.
 */
public class MedicalDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    /**
     * The Session factory.
     */
    SessionFactory sessionFactory = matc.persistence.SessionFactoryProvider.getSessionFactory();

    /**
     * Gets by medical id.
     *
     * @param id the id
     * @return the by medical id
     */
    public Medical getByMedicalId(int id) {
        Session session = sessionFactory.openSession();
        Medical medical = session.get(Medical.class, id);
        session.close();
        return medical;
    }

    /**
     * Update.
     *
     * @param medical the medical
     */
    public void update(Medical medical) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(medical);
        transaction.commit();
        session.close();
    }

    /**
     * Insert int.
     *
     * @param medical the medical
     * @return the int
     */
    public int insert(Medical medical) {
        int m_id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(medical);
        transaction.commit();
        m_id = medical.getMedicalId();
        session.close();
        return m_id;
    }

    /**
     * Delete.
     *
     * @param medical the medical
     */
    public void delete(Medical medical) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(medical);
        transaction.commit();
        session.close();
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    public List<Medical> getAll() {

        Session session = sessionFactory.openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Medical> query = builder.createQuery(Medical.class);
        Root<Medical> root = query.from(Medical.class);
        List<Medical> meds = session.createSelectionQuery( query ).getResultList();

        logger.debug("The list of meds " + meds);
        session.close();

        return meds;
    }

    /**
     * Gets by property equal.
     *
     * @param propertyName the property name
     * @param value        the value
     * @return the by property equal
     */
    public List<Medical> getByPropertyEqual(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for medical with " + propertyName + " = " + value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Medical> query = builder.createQuery(Medical.class);
        Root<Medical> root = query.from(Medical.class);
        query.select(root).where(builder.equal(root.get(propertyName), value));
        List<Medical> meds = session.createSelectionQuery( query ).getResultList();

        session.close();
        return meds;
    }

    /**
     * Gets by property like.
     *
     * @param propertyName the property name
     * @param value        the value
     * @return the by property like
     */
    public List<Medical> getByPropertyLike(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for medical with {} = {}",  propertyName, value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Medical> query = builder.createQuery(Medical.class);
        Root<Medical> root = query.from(Medical.class);
        Expression<String> propertyPath = root.get(propertyName);

        query.where(builder.like(propertyPath, "%" + value + "%"));

        List<Medical> meds = session.createQuery( query ).getResultList();
        session.close();
        return meds;
    }
}
