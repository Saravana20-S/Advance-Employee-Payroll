package com.bridgelabz.advemployeepayroll.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Utility class for establishing a connection
 * to the PostgreSQL payroll_service database.
 */
public class DBConnection {

    // Database URL
    private static final String URL =
            "jdbc:postgresql://localhost:5432/payroll_service";

    // PostgreSQL username
    private static final String USERNAME = "postgres";

    // PostgreSQL password
    private static final String PASSWORD = "12345";

    /**
     * Displays all JDBC drivers registered with DriverManager.
     */
    public static void printRegisteredDrivers() {

        System.out.println("Registered JDBC Drivers:");

        Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();

        while (drivers.hasMoreElements()) {
            System.out.println(drivers.nextElement());
        }
    }

    /**
     * Establishes and returns a database connection.
     *
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USERNAME,
                PASSWORD
        );
    }

}