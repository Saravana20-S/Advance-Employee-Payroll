package com.bridgelabz.advemployeepayroll;

import com.bridgelabz.advemployeepayroll.model.EmployeePayroll;
import com.bridgelabz.advemployeepayroll.service.EmployeePayrollService;
import com.bridgelabz.advemployeepayroll.util.DBConnection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

/**
 * Main class to test PostgreSQL JDBC connectivity
 * and retrieve employee payroll records.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("========== Advanced Employee Payroll ==========\n");

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
                        "\nConnected Successfully to payroll_service database.\n");

                // ==============================
                // UC2 : Retrieve Employee Payroll Data
                // ==============================

                EmployeePayrollService payrollService =
                        new EmployeePayrollService();

                List<EmployeePayroll> employeePayrollList =
                        payrollService.getEmployeePayrollList();

                System.out.println("Employee Payroll Records");
                System.out.println("-------------------------");

                employeePayrollList.forEach(System.out::println);

                // Close database connection
                connection.close();

                System.out.println(
                        "\nDatabase Connection Closed.");
            }

        } catch (ClassNotFoundException e) {

            System.out.println("PostgreSQL Driver Not Found.");

        } catch (SQLException e) {

            System.out.println("Database Connection Failed.");
            e.printStackTrace();
        }
    }
}