-- ===========================================================
-- UC12 : Soft Delete Employee Payroll
-- PostgreSQL
-- ===========================================================

-- ===========================================================
-- Step 1 : Add is_active column to employee table
-- ===========================================================

ALTER TABLE employee
ADD COLUMN IF NOT EXISTS is_active BOOLEAN DEFAULT TRUE;

-- ===========================================================
-- Step 2 : Ensure all existing employees are active
-- ===========================================================

UPDATE employee
SET is_active = TRUE
WHERE is_active IS NULL;

-- ===========================================================
-- Step 3 : View all employees
-- ===========================================================

SELECT
    employee_id,
    name,
    gender,
    start_date,
    is_active
FROM employee;

-- ===========================================================
-- Step 4 : Soft Delete Employee
-- Example : Rahul
-- ===========================================================

UPDATE employee
SET is_active = FALSE
WHERE name = 'Rahul';

-- ===========================================================
-- Step 5 : Verify Employee Status
-- ===========================================================

SELECT
    employee_id,
    name,
    is_active
FROM employee;

-- ===========================================================
-- Step 6 : Retrieve Only Active Employees
-- ===========================================================

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
    STRING_AGG(d.department_name, ', ') AS departments
FROM employee e
JOIN payroll p
    ON e.employee_id = p.employee_id
LEFT JOIN employee_department ed
    ON e.employee_id = ed.employee_id
LEFT JOIN department d
    ON ed.department_id = d.department_id
WHERE e.is_active = TRUE
GROUP BY
    e.employee_id,
    e.name,
    e.gender,
    e.start_date,
    p.basic_pay,
    p.deductions,
    p.taxable_pay,
    p.income_tax,
    p.net_pay
ORDER BY e.employee_id;

-- ===========================================================
-- Step 7 : Retrieve Active Employee By Name
-- ===========================================================

SELECT
    e.employee_id,
    e.name,
    p.basic_pay
FROM employee e
JOIN payroll p
    ON e.employee_id = p.employee_id
WHERE e.name = 'Terisa'
  AND e.is_active = TRUE;

-- ===========================================================
-- Step 8 : Payroll Statistics For Active Employees
-- ===========================================================

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
WHERE e.is_active = TRUE
GROUP BY e.gender
ORDER BY e.gender;

-- ===========================================================
-- End of UC12
-- ===========================================================