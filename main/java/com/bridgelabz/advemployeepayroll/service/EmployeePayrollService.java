package com.bridgelabz.advemployeepayroll.service;

import com.bridgelabz.advemployeepayroll.model.EmployeePayroll;
import com.bridgelabz.advemployeepayroll.model.PayrollStatistics;
import com.bridgelabz.advemployeepayroll.util.DBConnection;

import java.sql.*;

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
                    e.gender,
                    e.start_date,
                    p.basic_pay,
                    p.deductions,
                    p.taxable_pay,
                    p.income_tax,
                    p.net_pay,
                    d.department_name
                FROM employee e
                JOIN payroll p
                    ON e.employee_id = p.employee_id
                LEFT JOIN employee_department ed
                    ON e.employee_id = ed.employee_id
                LEFT JOIN department d
                    ON ed.department_id = d.department_id
                WHERE e.name = ?
                ORDER BY d.department_name
                """;

            employeeByNameStatement =
                    connection.prepareStatement(employeeByNameQuery);

            String employeeByDateRangeQuery = """
                SELECT
                    e.employee_id,
                    e.name,
                    e.gender,
                    e.start_date,
                    p.basic_pay,
                    p.deductions,
                    p.taxable_pay,
                    p.income_tax,
                    p.net_pay,
                    d.department_name
                FROM employee e
                JOIN payroll p
                    ON e.employee_id = p.employee_id
                LEFT JOIN employee_department ed
                    ON e.employee_id = ed.employee_id
                LEFT JOIN department d
                    ON ed.department_id = d.department_id
                WHERE e.start_date BETWEEN ? AND ?
                ORDER BY e.employee_id
                """;

            employeeByDateRangeStatement =
                    connection.prepareStatement(employeeByDateRangeQuery);

            String statisticsQuery = """
                SELECT
                    e.gender,
                    SUM(p.basic_pay) total_salary,
                    AVG(p.basic_pay) average_salary,
                    MIN(p.basic_pay) minimum_salary,
                    MAX(p.basic_pay) maximum_salary,
                    COUNT(*) employee_count
                FROM employee e
                JOIN payroll p
                    ON e.employee_id = p.employee_id
                GROUP BY e.gender
                ORDER BY e.gender
                """;

            payrollStatisticsStatement =
                    connection.prepareStatement(statisticsQuery);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    /**
     * Retrieves all employee payroll records.
     *
     * @return List of EmployeePayroll objects
     */
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
                e.gender,
                e.start_date,
                p.basic_pay,
                p.deductions,
                p.taxable_pay,
                p.income_tax,
                p.net_pay,
                d.department_name
            FROM employee e
            JOIN payroll p
                ON e.employee_id = p.employee_id
            LEFT JOIN employee_department ed
                ON e.employee_id = ed.employee_id
            LEFT JOIN department d
                ON ed.department_id = d.department_id
            ORDER BY e.employee_id
            """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            EmployeePayroll employee = null;
            int previousEmployeeId = -1;

            while (resultSet.next()) {

                int currentEmployeeId =
                        resultSet.getInt("employee_id");

                if (employee == null ||
                        currentEmployeeId != previousEmployeeId) {

                    employee =
                            getEmployeePayrollData(resultSet);

                    employeeList.add(employee);

                    previousEmployeeId =
                            currentEmployeeId;
                }

                String department =
                        resultSet.getString("department_name");

                if (department != null) {
                    employee.addDepartment(department);
                }
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
    /**
     * Retrieves employee by name.
     *
     * @param name employee name
     * @return EmployeePayroll object
     */
    public EmployeePayroll getEmployeeByName(String name) {

        try {

            employeeByNameStatement.setString(1, name);

            ResultSet resultSet =
                    employeeByNameStatement.executeQuery();

            EmployeePayroll employee = null;

            while (resultSet.next()) {

                if (employee == null) {

                    employee =
                            getEmployeePayrollData(resultSet);

                }

                String department =
                        resultSet.getString("department_name");

                if (department != null) {

                    employee.addDepartment(department);

                }
            }

            return employee;

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
    private EmployeePayroll getEmployeePayrollData(ResultSet rs)
            throws SQLException {

        EmployeePayroll employee =
                new EmployeePayroll(

                        rs.getInt("employee_id"),

                        rs.getString("name"),

                        rs.getDouble("basic_pay"),

                        rs.getDate("start_date").toLocalDate()

                );

        employee.setGender(
                rs.getString("gender").charAt(0));

        employee.setBasicPay(
                rs.getDouble("basic_pay"));

        employee.setDeductions(
                rs.getDouble("deductions"));

        employee.setTaxablePay(
                rs.getDouble("taxable_pay"));

        employee.setIncomeTax(
                rs.getDouble("income_tax"));

        employee.setNetPay(
                rs.getDouble("net_pay"));

        return employee;
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


    /**
     * Inserts employee details and returns generated employee id.
     */
    private int addEmployeeDetails(String name,
                                   char gender,
                                   LocalDate startDate)
            throws SQLException {

        String sql = """
            INSERT INTO employee(name, gender, start_date)
            VALUES (?, ?, ?)
            """;

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, name);
            statement.setString(2, String.valueOf(gender));
            statement.setDate(3, Date.valueOf(startDate));

            statement.executeUpdate();

            ResultSet generatedKeys =
                    statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        }

        return -1;
    }

    private void addEmployeeDepartment(int employeeId,
                                       int departmentId)
            throws SQLException {

        String sql = """
            INSERT INTO employee_department(employee_id,department_id)
            VALUES(?,?)
            """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, employeeId);
            statement.setInt(2, departmentId);

            statement.executeUpdate();
        }
    }


    private void addPayrollDetails(
            int employeeId,
            double basicPay)
            throws SQLException {

        double deductions = basicPay * 0.20;
        double taxablePay = basicPay - deductions;
        double incomeTax = taxablePay * 0.10;
        double netPay = basicPay - incomeTax;

        String sql = """
            INSERT INTO payroll(
                employee_id,
                basic_pay,
                deductions,
                taxable_pay,
                income_tax,
                net_pay)
            VALUES(?,?,?,?,?,?)
            """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, employeeId);
            statement.setDouble(2, basicPay);
            statement.setDouble(3, deductions);
            statement.setDouble(4, taxablePay);
            statement.setDouble(5, incomeTax);
            statement.setDouble(6, netPay);

            statement.executeUpdate();
        }
    }

    /**
     * Adds a new employee into the Payroll Service.
     */
    public EmployeePayroll addEmployee(
            String name,
            char gender,
            double basicPay,
            LocalDate startDate,
            List<Integer> departmentIds) {

        try {

            connection.setAutoCommit(false);

            int employeeId =
                    addEmployeeDetails(
                            name,
                            gender,
                            startDate);

            addPayrollDetails(
                    employeeId,
                    basicPay);

            // Employee can belong to multiple departments
            for (Integer departmentId : departmentIds) {

                addEmployeeDepartment(
                        employeeId,
                        departmentId);
            }

            connection.commit();

            return getEmployeeByName(name);

        } catch (SQLException e) {

            try {

                connection.rollback();

                System.out.println(
                        "Transaction Rolled Back.");

            } catch (SQLException ex) {

                ex.printStackTrace();

            }

            e.printStackTrace();

        } finally {

            try {

                connection.setAutoCommit(true);

            } catch (SQLException e) {

                e.printStackTrace();

            }

        }

        return null;
    }

}