package com.misfit.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

/**
 * The type Validation util.
 */
public class ValidationUtil {
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    /**
     * Validates an object based on its annotations.
     *
     * @param <T>    The type of the entity.
     * @param entity The object to validate.
     * @return A set of constraint violations. If the set is empty, the entity is valid.
     */
    public static <T> Set<ConstraintViolation<T>> validate(T entity) {
        return validator.validate(entity);
    }
}