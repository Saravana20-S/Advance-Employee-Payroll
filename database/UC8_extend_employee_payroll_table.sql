-- ==========================================================
-- UC8 : Extend Employee Payroll Table
-- Description :
--   Add phone, address and department columns to
--   the employee_payroll table.
-- Database : PostgreSQL
-- ==========================================================

-- Switch to the payroll_service database before executing.
-- psql command:
-- \c payroll_service


-- ==========================================================
-- Step 1 : Add Phone Number
-- ==========================================================

ALTER TABLE employee_payroll
ADD COLUMN phone VARCHAR(15);


-- ==========================================================
-- Step 2 : Add Address with Default Value
-- ==========================================================

ALTER TABLE employee_payroll
ADD COLUMN address VARCHAR(255) DEFAULT 'Not Available';


-- ==========================================================
-- Step 3 : Add Department
-- ==========================================================

ALTER TABLE employee_payroll
ADD COLUMN department VARCHAR(100);


-- ==========================================================
-- Step 4 : Update Existing Records
-- Existing rows need a department value before
-- making the column NOT NULL.
-- ==========================================================

UPDATE employee_payroll
SET department = 'General'
WHERE department IS NULL;


-- ==========================================================
-- Step 5 : Make Department Mandatory
-- ==========================================================

ALTER TABLE employee_payroll
ALTER COLUMN department SET NOT NULL;


-- ==========================================================
-- Step 6 : Verify Table Structure
-- ==========================================================

SELECT *
FROM employee_payroll;