package com.bridgelabz.advemployeepayroll;

import com.bridgelabz.advemployeepayroll.model.EmployeePayroll;
import com.bridgelabz.advemployeepayroll.service.EmployeePayrollService;
import com.bridgelabz.advemployeepayroll.util.DBConnection;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
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

            // Load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");

            System.out.println("PostgreSQL Driver Loaded Successfully.\n");

            // Display all registered JDBC Drivers
            System.out.println("Registered JDBC Drivers:");

            Enumeration<Driver> drivers =
                    DriverManager.getDrivers();

            while (drivers.hasMoreElements()) {
                System.out.println(drivers.nextElement());
            }

            System.out.println();

            // Establish Database Connection
            Connection connection = DBConnection.getConnection();

            if (connection != null) {

                System.out.println(
                        "Connected Successfully to payroll_service database.\n");

                EmployeePayrollService payrollService =
                        EmployeePayrollService.getInstance();

                // ==========================================
                // UC2 : Retrieve Employee Payroll Records
                // ==========================================

                System.out.println("Employee Payroll Records");
                System.out.println("------------------------");

                List<EmployeePayroll> employeeList =
                        payrollService.getEmployeePayrollList();

                employeeList.forEach(System.out::println);

                // ==========================================
                // UC3 & UC4 : Update Employee Salary
                // ==========================================

                System.out.println("\nUpdating Terisa's Salary...");
                System.out.println("---------------------------");

                EmployeePayroll updatedEmployee =
                        payrollService.updateEmployeeSalary(
                                "Terisa",
                                3000000.00);

                if (updatedEmployee != null) {

                    System.out.println("Salary Updated Successfully.");

                    System.out.println(updatedEmployee);

                } else {

                    System.out.println("Salary Update Failed.");
                }

                // ==========================================
                // UC5 : Retrieve Employees by Date Range
                // ==========================================

                System.out.println("\nEmployees Joined Between 2018-01-01 and Today");
                System.out.println("---------------------------------------------");

                List<EmployeePayroll> employees =
                        payrollService.getEmployeesByDateRange(
                                LocalDate.of(2018, 1, 1),
                                LocalDate.now());

                employees.forEach(System.out::println);

                // ==========================================
                // UC6 : Payroll Statistics
                // ==========================================

                System.out.println("\nPayroll Statistics By Gender");
                System.out.println("----------------------------");

                payrollService
                        .getPayrollStatisticsByGender()
                        .forEach(System.out::println);

                // ==========================================
                // UC7, UC8 & UC9 : Add New Employee
                // ==========================================

                System.out.println("\nAdding New Employee");
                System.out.println("-------------------");

                EmployeePayroll newEmployee =
                        payrollService.addEmployee(

                                "Rahul",

                                'M',

                                4500000.00,

                                LocalDate.now(),

                                List.of(1, 2)

                        );

                if (newEmployee != null) {

                    System.out.println("Employee Added Successfully.");

                    System.out.println(newEmployee);

                } else {

                    System.out.println("Unable to Add Employee.");
                }

                connection.close();

                System.out.println("\nDatabase Connection Closed.");
            }

        } catch (ClassNotFoundException e) {

            System.out.println("PostgreSQL Driver Not Found.");

        } catch (SQLException e) {

            System.out.println("Database Connection Failed.");

            e.printStackTrace();
        }
    }
}