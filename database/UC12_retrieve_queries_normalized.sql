-- ==========================================================
-- UC12 : Retrieve Queries for Normalized Payroll Database
-- Description :
--   Re-implement UC4, UC5 and UC7 queries using the
--   normalized database schema.
-- Database : PostgreSQL
-- ==========================================================

-- Connect to the payroll_service database
-- \c payroll_service


-- ==========================================================
-- UC4 : Retrieve Complete Employee Payroll Details
-- ==========================================================

SELECT
    e.employee_id,
    e.name,
    e.gender,
    e.phone,
    e.address,
    e.start_date,
    d.department_name,
    p.basic_pay,
    p.deductions,
    p.taxable_pay,
    p.income_tax,
    p.net_pay
FROM employee e
JOIN payroll p
    ON e.employee_id = p.employee_id
LEFT JOIN employee_department ed
    ON e.employee_id = ed.employee_id
LEFT JOIN department d
    ON ed.department_id = d.department_id
ORDER BY e.employee_id;


-- ==========================================================
-- UC5 : Retrieve Salary of Bill
-- ==========================================================

SELECT
    e.name,
    p.basic_pay
FROM employee e
JOIN payroll p
    ON e.employee_id = p.employee_id
WHERE e.name = 'Bill';


-- ==========================================================
-- UC5 : Retrieve Employees Joined Between
--       2018-01-01 and Current Date
-- ==========================================================

SELECT
    employee_id,
    name,
    gender,
    start_date
FROM employee
WHERE start_date
BETWEEN CAST('2018-01-01' AS DATE)
AND CURRENT_DATE;


-- ==========================================================
-- UC7 : Total Salary by Gender
-- ==========================================================

SELECT
    e.gender,
    SUM(p.basic_pay) AS total_salary
FROM employee e
JOIN payroll p
ON e.employee_id = p.employee_id
GROUP BY e.gender;


-- ==========================================================
-- UC7 : Average Salary by Gender
-- ==========================================================

SELECT
    e.gender,
    AVG(p.basic_pay) AS average_salary
FROM employee e
JOIN payroll p
ON e.employee_id = p.employee_id
GROUP BY e.gender;


-- ==========================================================
-- UC7 : Minimum Salary by Gender
-- ==========================================================

SELECT
    e.gender,
    MIN(p.basic_pay) AS minimum_salary
FROM employee e
JOIN payroll p
ON e.employee_id = p.employee_id
GROUP BY e.gender;


-- ==========================================================
-- UC7 : Maximum Salary by Gender
-- ==========================================================

SELECT
    e.gender,
    MAX(p.basic_pay) AS maximum_salary
FROM employee e
JOIN payroll p
ON e.employee_id = p.employee_id
GROUP BY e.gender;


-- ==========================================================
-- UC7 : Employee Count by Gender
-- ==========================================================

SELECT
    gender,
    COUNT(*) AS employee_count
FROM employee
GROUP BY gender;