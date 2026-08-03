package com.bridgelabz.advemployeepayroll.service;

import com.bridgelabz.advemployeepayroll.model.EmployeePayroll;
import com.bridgelabz.advemployeepayroll.model.PayrollStatistics;
import com.bridgelabz.advemployeepayroll.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for all Employee Payroll
 * database operations.
 *
 * This class is implemented as a Singleton so that
 * the database connection and PreparedStatements are
 * created only once and reused throughout the program.
 */
public class EmployeePayrollService {

    // Singleton Instance
    private static EmployeePayrollService payrollService;

    // Cached database connection
    private Connection connection;

    // Cached PreparedStatement
    private PreparedStatement employeeByNameStatement;

    // Cached PreparedStatement to retrieve employees by date range
    private PreparedStatement employeeByDateRangeStatement;

    private PreparedStatement payrollStatisticsStatement;

    /**
     * Private constructor.
     */
    private EmployeePayrollService() {
        prepareStatements();
    }

    /**
     * Returns Singleton instance.
     *
     * @return EmployeePayrollService object
     */
    public static EmployeePayrollService getInstance() {

        if (payrollService == null) {
            payrollService = new EmployeePayrollService();
        }

        return payrollService;
    }

    /**
     * Creates database connection and caches
     * PreparedStatements.
     */
    private void prepareStatements() {

        try {

            connection = DBConnection.getConnection();

            String employeeByNameQuery = """
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

            String employeeByDateRangeQuery = """
        SELECT
            e.employee_id,
            e.name,
            p.basic_pay,
            e.start_date
        FROM employee e
        JOIN payroll p
        ON e.employee_id = p.employee_id
        WHERE e.start_date BETWEEN ? AND ?
        ORDER BY e.start_date
        """;

            String statisticsQuery = """
        SELECT
            e.gender,
            SUM(p.basic_pay) AS total_salary,
            AVG(p.basic_pay) AS average_salary,
            MIN(p.basic_pay) AS minimum_salary,
            MAX(p.basic_pay) AS maximum_salary,
            COUNT(*) AS employee_count
        FROM employee e
        JOIN payroll p
        ON e.employee_id = p.employee_id
        GROUP BY e.gender
        ORDER BY e.gender
        """;

            payrollStatisticsStatement =
                    connection.prepareStatement(statisticsQuery);

//            employeeByDateRangeStatement =
//                    connection.prepareStatement(employeeByDateRangeQuery);

//            employeeByNameStatement =
//                    connection.prepareStatement(employeeByNameQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all employee payroll records.
     *
     * @return List of EmployeePayroll objects
     */
    public List<EmployeePayroll> getEmployeePayrollList() {

        List<EmployeePayroll> employeeList =
                new ArrayList<>();

        String sql = """
                SELECT
                    e.employee_id,
                    e.name,
                    p.basic_pay,
                    e.start_date
                FROM employee e
                JOIN payroll p
                ON e.employee_id = p.employee_id
                ORDER BY e.employee_id
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                employeeList.add(
                        getEmployeePayrollData(resultSet)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeList;
    }

    /**
     * Retrieves employee by name using the
     * cached PreparedStatement.
     *
     * @param name employee name
     * @return EmployeePayroll object
     */
    public EmployeePayroll getEmployeeByName(String name) {

        try {

            employeeByNameStatement.setString(1, name);

            ResultSet resultSet =
                    employeeByNameStatement.executeQuery();

            if (resultSet.next()) {

                return getEmployeePayrollData(resultSet);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Updates employee basic pay using
     * JDBC PreparedStatement.
     *
     * @param employeeName employee name
     * @param basicPay updated salary
     * @return Updated EmployeePayroll object
     */
    public EmployeePayroll updateEmployeeSalary(
            String employeeName,
            double basicPay) {

        String sql = """
                UPDATE payroll
                SET basic_pay = ?
                WHERE employee_id =
                (
                    SELECT employee_id
                    FROM employee
                    WHERE name = ?
                )
                """;

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {

            preparedStatement.setDouble(1, basicPay);
            preparedStatement.setString(2, employeeName);

            int rowsAffected =
                    preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                return getEmployeeByName(employeeName);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Checks whether EmployeePayroll object
     * and database are synchronized.
     *
     * @param employee EmployeePayroll object
     * @return true if synchronized
     */
    public boolean checkEmployeePayrollInSync(
            EmployeePayroll employee) {

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

    /**
     * Creates EmployeePayroll object from
     * current ResultSet row.
     *
     * @param resultSet database ResultSet
     * @return EmployeePayroll object
     * @throws SQLException if ResultSet access fails
     */
    private EmployeePayroll getEmployeePayrollData(
            ResultSet resultSet)
            throws SQLException {

        return new EmployeePayroll(

                resultSet.getInt("employee_id"),

                resultSet.getString("name"),

                resultSet.getDouble("basic_pay"),

                resultSet.getDate("start_date")
                        .toLocalDate()

        );
    }



    /**
     * Retrieves all employees who joined
     * between the given start and end dates.
     *
     * @param startDate starting date
     * @param endDate ending date
     * @return list of employees
     */
    public List<EmployeePayroll> getEmployeesByDateRange(
            LocalDate startDate,
            LocalDate endDate) {

        List<EmployeePayroll> employeeList =
                new ArrayList<>();

        try {

            employeeByDateRangeStatement.setDate(
                    1,
                    java.sql.Date.valueOf(startDate));

            employeeByDateRangeStatement.setDate(
                    2,
                    java.sql.Date.valueOf(endDate));

            ResultSet resultSet =
                    employeeByDateRangeStatement.executeQuery();

            while (resultSet.next()) {

                employeeList.add(
                        getEmployeePayrollData(resultSet)
                );

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeList;
    }

    /**
     * Retrieves payroll statistics
     * grouped by gender.
     *
     * @return list of payroll statistics
     */
    public List<PayrollStatistics> getPayrollStatisticsByGender() {

        List<PayrollStatistics> statistics =
                new ArrayList<>();

        try (ResultSet resultSet =
                     payrollStatisticsStatement.executeQuery()) {

            while (resultSet.next()) {

                statistics.add(

                        new PayrollStatistics(

                                resultSet.getString("gender").charAt(0),

                                resultSet.getDouble("total_salary"),

                                resultSet.getDouble("average_salary"),

                                resultSet.getDouble("minimum_salary"),

                                resultSet.getDouble("maximum_salary"),

                                resultSet.getInt("employee_count")

                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return statistics;
    }

}