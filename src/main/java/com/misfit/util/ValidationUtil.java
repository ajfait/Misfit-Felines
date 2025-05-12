package com.misfit.util;

import jakarta.validation.*;

import java.util.Set;

/**
 * The `ValidationUtil` class provides a static method to validate objects using Bean Validation API in
 * Java.
 */
public class ValidationUtil {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    /**
     * The function `validate` takes an entity of type T and returns a set of constraint violations for
     * that entity.
     * 
     * @param entity The `entity` parameter in the `validate` method represents the object that you want to
     * validate against constraints. This method takes an object of type `T` and returns a set of
     * constraint violations for that object.
     * @return A Set of ConstraintViolation objects for the given entity is being returned.
     */
    public static <T> Set<ConstraintViolation<T>> validate(T entity) {
        return validator.validate(entity);
    }
}