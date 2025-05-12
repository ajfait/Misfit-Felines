package com.misfit.persistence;

import java.io.*;
import java.sql.*;
import java.util.Properties;

import org.apache.logging.log4j.*;

/**
 * Provides access the database
 * Created on 8/31/16.
 *
 * @author pwaite
 */
public class Database {
    // Initializes logger object
    private final Logger logger = LogManager.getLogger(this.getClass());

    // Creates an object of the class Database
    private static final Database instance = new Database();

    // Creates a Properties object
    private Properties properties;

    // Creates a Connection object
    private Connection connection;

    // Private constructor prevents instantiating this class anywhere else
    private Database() {
        loadProperties();
    }

    /**
     * Method used to load properties.
     */
    private void loadProperties() {
        properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("/database.properties"));
        } catch (IOException ioe) {
            logger.error("Database.loadProperties()...Cannot load the properties file");
        } catch (Exception e) {
            logger.error("Database.loadProperties()..." + e);
        }
    }

    /**
     * Gets the database object.
     *
     * @return instance
     */
    public static Database getInstance() {
        return instance;
    }

    /**
     * Gets the connection object.
     *
     * @return connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Connects to the database.
     *
     * @throws Exception if driver not found
     */
    public void connect() throws Exception {
        if (connection != null)
            return;

        try {
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            throw new Exception("Database.connect()... Error: MySQL Driver not found");
        }

        // Accesses connection credentials from the properties file
        String url = properties.getProperty("url");
        connection = DriverManager.getConnection(url, properties.getProperty("username"), properties.getProperty("password"));
    }

    /**
     * Disconnects from the database.
     */
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Cannot close connection" + e);
            }
        }

        connection = null;
    }

    /**
     * Runs the sql.
     *
     * @param sqlFile the sql file to be read and executed line by line
     */
    public void runSQL(String sqlFile) {

        Statement stmt = null;
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(classloader.getResourceAsStream(sqlFile)))) {

            connect();
            stmt = connection.createStatement();

            String sql = "";
            while (br.ready()) {
                char inputValue = (char) br.read();

                if (inputValue == ';') {
                    stmt.executeUpdate(sql);
                    sql = "";
                } else
                    sql += inputValue;
            }

        } catch (SQLException se) {
            logger.error("SQL Exception" + se);
        } catch (Exception e) {
            logger.error("Exception" + e);
        } finally {
            disconnect();
        }
    }
}