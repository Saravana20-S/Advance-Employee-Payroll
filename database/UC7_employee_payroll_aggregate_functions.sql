-- ==========================================================
-- UC7 : Perform Aggregate Operations on Employee Payroll Data
-- Description :
--   Perform payroll analysis using aggregate functions
--   grouped by employee gender.
-- Database : PostgreSQL
-- ==========================================================

-- Switch to the payroll_service database before executing.
-- psql command:
-- \c payroll_service


-- ==========================================================
-- 1. Total Salary by Gender
-- ==========================================================

SELECT
    gender,
    SUM(salary) AS total_salary
FROM employee_payroll
GROUP BY gender;


-- ==========================================================
-- 2. Average Salary by Gender
-- ==========================================================

SELECT
    gender,
    AVG(salary) AS average_salary
FROM employee_payroll
GROUP BY gender;


-- ==========================================================
-- 3. Minimum Salary by Gender
-- ==========================================================

SELECT
    gender,
    MIN(salary) AS minimum_salary
FROM employee_payroll
GROUP BY gender;


-- ==========================================================
-- 4. Maximum Salary by Gender
-- ==========================================================

SELECT
    gender,
    MAX(salary) AS maximum_salary
FROM employee_payroll
GROUP BY gender;


-- ==========================================================
-- 5. Number of Employees by Gender
-- ==========================================================

SELECT
    gender,
    COUNT(*) AS employee_count
FROM employee_payroll
GROUP BY gender;