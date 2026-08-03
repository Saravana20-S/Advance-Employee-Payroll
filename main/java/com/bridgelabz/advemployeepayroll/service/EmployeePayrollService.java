package com.bridgelabz.advemployeepayroll.service;

import com.bridgelabz.advemployeepayroll.model.EmployeePayroll;
import com.bridgelabz.advemployeepayroll.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for payroll database operations.
 */
public class EmployeePayrollService {

    /**
     * Retrieves all employee payroll records
     * from the database.
     *
     * @return list of employee payroll objects
     */
    public List<EmployeePayroll> getEmployeePayrollList() {

        List<EmployeePayroll> employeeList =
                new ArrayList<>();

        String query =
                "SELECT id, name, salary, start_date FROM employee_payroll";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                EmployeePayroll employee =
                        new EmployeePayroll(

                                resultSet.getInt("id"),

                                resultSet.getString("name"),

                                resultSet.getDouble("salary"),

                                resultSet.getDate("start_date")
                                        .toLocalDate()
                        );

                employeeList.add(employee);
            }

        } catch (SQLException e) {

            System.out.println("Unable to retrieve employee payroll data.");

            e.printStackTrace();
        }

        return employeeList;
    }


    /**
     * Updates the basic pay of an employee in the database.
     *
     * @param employeeName Employee name
     * @param basicPay Updated basic pay
     * @return true if updated successfully, otherwise false
     */
    public boolean updateEmployeeSalary(String employeeName, double basicPay) {

        String sql = """
            UPDATE payroll
            SET basic_pay = ?
            WHERE employee_id = (
                SELECT employee_id
                FROM employee
                WHERE name = ?
            )
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {

            preparedStatement.setDouble(1, basicPay);
            preparedStatement.setString(2, employeeName);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}