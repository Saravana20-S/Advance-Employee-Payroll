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
    public List<EmployeePayroll> getEmployeePayrollList(String terisa) {

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


    public EmployeePayroll getEmployeeByName(String name) {

        String sql = """
            SELECT
                e.employee_id,
                e.name,
                p.basic_pay,
                e.start_date
            FROM employee e
            JOIN payroll p
            ON e.employee_id = p.employee_id
            WHERE e.name = ?
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {

                return new EmployeePayroll(
                        rs.getInt("employee_id"),
                        rs.getString("name"),
                        rs.getDouble("basic_pay"),
                        rs.getDate("start_date").toLocalDate()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public EmployeePayroll updateEmployeeSalary(String employeeName,
                                                double basicPay) {

        String sql = """
            UPDATE payroll
            SET basic_pay=?
            WHERE employee_id=
            (
                SELECT employee_id
                FROM employee
                WHERE name=?
            )
            """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setDouble(1, basicPay);
            ps.setString(2, employeeName);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                return getEmployeeByName(employeeName);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Checks whether the employee object's salary
     * is synchronized with the database.
     *
     * @param employee EmployeePayroll object
     * @return true if object and database values match
     */
    public boolean checkEmployeePayrollInSync(EmployeePayroll employee) {

        EmployeePayroll employeeFromDB =
                getEmployeeByName(employee.getName());

        if (employeeFromDB == null) {
            return false;
        }

        return Double.compare(
                employee.getBasicPay(),
                employeeFromDB.getBasicPay()
        ) == 0;
    }
}