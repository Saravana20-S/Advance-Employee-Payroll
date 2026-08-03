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
 * and Employee Payroll operations.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("========== Advanced Employee Payroll ==========\n");

        try {

            // Load PostgreSQL Driver
            Class.forName("org.postgresql.Driver");

            System.out.println("PostgreSQL Driver Loaded Successfully.\n");

            // Display Registered JDBC Drivers
            System.out.println("Registered JDBC Drivers:");

            Enumeration<Driver> drivers =
                    DriverManager.getDrivers();

            while (drivers.hasMoreElements()) {
                System.out.println(drivers.nextElement());
            }

            System.out.println();

            Connection connection = DBConnection.getConnection();

            if (connection != null) {

                System.out.println("Connected Successfully.\n");

                EmployeePayrollService payrollService =
                        EmployeePayrollService.getInstance();

                // =============================
                // UC2 : Read Employee Payroll
                // =============================

                List<EmployeePayroll> employeeList =
                        payrollService.getEmployeePayrollList();

                System.out.println("Employee Payroll Records");
                System.out.println("------------------------");

                employeeList.forEach(System.out::println);

                // =============================
                // UC3 & UC4 : Update Salary
                // =============================

                EmployeePayroll employee =
                        payrollService.updateEmployeeSalary(
                                "Terisa",
                                3000000.00);

                if (employee != null) {

                    System.out.println("\nSalary Updated Successfully\n");

                    System.out.println(employee);

                } else {

                    System.out.println("\nSalary Update Failed");

                }

                connection.close();

                System.out.println("\nDatabase Connection Closed.");

            }

        } catch (ClassNotFoundException e) {

            System.out.println("Driver Not Found.");

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

}