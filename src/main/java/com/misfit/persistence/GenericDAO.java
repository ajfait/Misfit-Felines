package com.misfit.persistence;

import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.*;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

/**
 * The `GenericDAO` class provides generic data access operations for entities of a specified
 * type using Hibernate ORM.
 */
public class GenericDAO<T> {
    private final Class<T> type;

    /**
     * The constructor `public GenericDAO(Class<T> type)` in the `GenericDAO` class is initializing the
     * `type` field of the class with the generic type `T` that is specified when creating an instance of
     * `GenericDAO`. This constructor allows you to specify the type of entities that this `GenericDAO`
     * will be working with. It sets the `type` field to the class type provided during instantiation,
     * enabling the class to work with entities of that specific type throughout its methods.
     */
    public GenericDAO(Class<T> type) {
        this.type = type;
    }

    /**
     * The function `getSession()` returns a new session from the session factory.
     * 
     * @return An instance of the `Session` class is being returned.
     */
    private Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();
    }

    /**
     * The `insert` function saves a given entity to the database and returns the generated ID.
     * 
     * @param entity The `entity` parameter in the `insert` method represents the object that you want to
     * save in the database. This object should be an instance of a class that is mapped to a database
     * table using an ORM (Object-Relational Mapping) framework like Hibernate. The method saves this
     * entity in the
     * @return The method `insert` is returning an integer value, which is the ID of the entity that was
     * saved in the database.
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
     * The `getAll` function retrieves all entities of type `T` using Hibernate Criteria API.
     * 
     * @return A List of objects of type T is being returned.
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
     * This Java function retrieves an entity by its ID from the database using Hibernate.
     * 
     * @param id The `id` parameter is an integer value that represents the unique identifier of the entity
     * you want to retrieve from the database.
     * @return The method `getById` returns an entity of type `T` with the specified `id`.
     */
    public T getById(int id) {
        Session session = getSession();
        T entity = session.get(type, id);
        session.close();
        return entity;
    }

    /**
     * The function getByField retrieves a single entity of type T from the database based on a specified
     * field and value.
     * 
     * @param fieldName fieldName is a String parameter that represents the name of the field in the entity
     * class for which you want to search for a specific value.
     * @param value The `value` parameter in the `getByField` method represents the value that you want to
     * use as a filter criteria when querying the database. This value will be used to find an entity in
     * the database where the specified field matches the provided value.
     * @return The method `getByField` returns an object of type T based on the provided field name and
     * value. It executes a Hibernate query to retrieve a unique result from the database where the
     * specified field matches the given value.
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
     * The function retrieves a list of objects of type T based on a specified field and value using
     * Hibernate HQL.
     * 
     * @param fieldName fieldName is a String representing the name of the field in the entity for which
     * you want to retrieve records.
     * @param value The `value` parameter in the `getByFieldList` method represents the value that you want
     * to use as a filter criteria when querying the database. This value will be used to filter the
     * results based on the specified `fieldName`.
     * @return A List of objects of type T that match the specified field name and value.
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
     * The `update` function in Java updates an entity in the database using Hibernate.
     * 
     * @param entity The `entity` parameter in the `update` method represents the object that you want to
     * update in the database. This object should be an instance of a class that is mapped to a database
     * table in your Hibernate configuration. When you call the `update` method with an entity object, it
     * will update
     */
    public void update(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.merge(entity);
        transaction.commit();
        session.close();
    }

    /**
     * The `delete` function deletes an entity from the database using Hibernate in a transactional manner.
     * 
     * @param entity The `entity` parameter in the `delete` method represents the object that you want to
     * delete from the database. This object should be an instance of the entity class that is mapped to a
     * database table. When you call the `delete` method with an entity object as the argument, it will
     * delete
     */
    public void delete(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
        session.close();
    }
}