-- ==========================================================
-- UC11 : Aggregate Queries on Normalized Database
-- ==========================================================


-- Total Salary by Gender

SELECT
    e.gender,
    SUM(p.basic_pay) AS total_salary
FROM employee e
JOIN payroll p
ON e.employee_id = p.employee_id
GROUP BY e.gender;


-- Average Salary

SELECT
    e.gender,
    AVG(p.basic_pay) AS average_salary
FROM employee e
JOIN payroll p
ON e.employee_id = p.employee_id
GROUP BY e.gender;


-- Minimum Salary

SELECT
    e.gender,
    MIN(p.basic_pay) AS minimum_salary
FROM employee e
JOIN payroll p
ON e.employee_id = p.employee_id
GROUP BY e.gender;


-- Maximum Salary

SELECT
    e.gender,
    MAX(p.basic_pay) AS maximum_salary
FROM employee e
JOIN payroll p
ON e.employee_id = p.employee_id
GROUP BY e.gender;


-- Employee Count

SELECT
    e.gender,
    COUNT(*) AS employee_count
FROM employee e
GROUP BY e.gender;