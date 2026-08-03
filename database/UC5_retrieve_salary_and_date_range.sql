-- ==========================================================
-- UC5 : Retrieve Employee Salary and Employees by Date Range
-- Description :
--   1. Retrieve the salary of a particular employee.
--   2. Retrieve employees whose joining date falls
--      within a specified date range.
-- Database : PostgreSQL
-- ==========================================================

-- Switch to the payroll_service database before executing.
-- psql command:
-- \c payroll_service


-- ==========================================================
-- Query 1 : Retrieve Bill's Salary
-- ==========================================================

SELECT salary
FROM employee_payroll
WHERE name = 'Bill';


-- ==========================================================
-- Query 2 : Retrieve Employees Joined Between
--           2018-01-01 and Today's Date
-- ==========================================================

SELECT *
FROM employee_payroll
WHERE start_date BETWEEN
      CAST('2018-01-01' AS DATE)
      AND CURRENT_DATE;


-- Refactor UC12
SELECT e.name, p.basic_pay
FROM employee e
JOIN payroll p
ON e.employee_id = p.employee_id
WHERE e.name = 'Bill';