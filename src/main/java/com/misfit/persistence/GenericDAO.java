package com.misfit.persistence;

import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

/**
 * The type Generic dao.
 *
 * @param <T> the type parameter
 */
public class GenericDAO<T> {
    private final Class<T> type;

    /**
     * Instantiates a new Generic dao.
     *
     * @param type the type
     */
    public GenericDAO(Class<T> type) {
        this.type = type;
    }

    private Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();
    }

    /**
     * Inserts.
     *
     * @param entity the entity
     * @return the int
     */
    public int insert(T entity) {
        int id = 0;
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        id = (int) session.save(entity);
        transaction.commit();
        session.close();
        return id;
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    public List<T> getAll() {
        try (Session session = getSession()) {
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            query.from(type);
            return session.createSelectionQuery(query).getResultList();
        }
    }

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    public T getById(int id) {
        Session session = getSession();
        T entity = session.get(type, id);
        session.close();
        return entity;
    }

    /**
     * Gets by field.
     *
     * @param fieldName the field name
     * @param value     the value
     * @return the by field
     */
    public T getByField(String fieldName, Object value) {
        Session session = getSession();
        try {
            String hql = "FROM " + type.getName() + " WHERE " + fieldName + " = :value";
            return session.createQuery(hql, type).setParameter("value", value).uniqueResult();
        } finally {
            session.close();
        }
    }

    /**
     * Gets by field list.
     *
     * @param fieldName the field name
     * @param value     the value
     * @return the by field list
     */
    public List<T> getByFieldList(String fieldName, Object value) {
        Session session = getSession();
        try {
            String hql = "FROM " + type.getName() + " WHERE " + fieldName + " = :value";
            return session.createQuery(hql, type).setParameter("value", value).getResultList();
        } finally {
            session.close();
        }
    }

    /**
     * Updates.
     *
     * @param entity the entity
     */
    public void update(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.merge(entity);
        transaction.commit();
        session.close();
    }

    /**
     * Deletes.
     *
     * @param entity the entity
     */
    public void delete(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
        session.close();
    }
}
