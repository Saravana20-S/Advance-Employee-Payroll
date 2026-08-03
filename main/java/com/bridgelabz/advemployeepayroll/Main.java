package com.bridgelabz.advemployeepayroll;

import com.bridgelabz.advemployeepayroll.util.DBConnection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * Main class to test PostgreSQL JDBC connectivity.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("=== Advanced Employee Payroll ===\n");

        try {

            // Load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");

            System.out.println("PostgreSQL Driver Loaded Successfully.\n");

            // Display all registered JDBC drivers
            System.out.println("Registered JDBC Drivers:");

            Enumeration<Driver> drivers = DriverManager.getDrivers();

            while (drivers.hasMoreElements()) {
                System.out.println(drivers.nextElement());
            }

            System.out.println();

            // Establish database connection
            Connection connection = DBConnection.getConnection();

            if (connection != null) {

                System.out.println(
                        "Connected Successfully to payroll_service database."
                );

                connection.close();

                System.out.println(
                        "Database Connection Closed."
                );
            }

        } catch (ClassNotFoundException e) {

            System.out.println(
                    "PostgreSQL Driver Not Found."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Database Connection Failed."
            );

            e.printStackTrace();
        }

    }
}