package com.misfit.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * @author Eric Knapp
 */
public interface PropertiesLoader {
    default Properties loadProperties(String propertiesFilePath) throws Exception {
        Logger logger = LogManager.getLogger(this.getClass());
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream(propertiesFilePath));
        } catch (IOException ioException) {
            logger.error(ioException);
            throw ioException;
        } catch (Exception exception) {
            logger.error(exception);
            throw exception;
        }
        return properties;
    }
}