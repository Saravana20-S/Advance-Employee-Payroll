-- ==========================================================
-- UC6 : Add Gender Column and Update Employee Records
-- Description :
--   1. Add a new gender column to employee_payroll.
--   2. Update existing employee records with gender.
-- Database : PostgreSQL
-- ==========================================================

-- Switch to the payroll_service database before executing.
-- psql command:
-- \c payroll_service


-- ==========================================================
-- Step 1 : Add Gender Column
-- ==========================================================

ALTER TABLE employee_payroll
ADD COLUMN gender CHAR(1);


-- ==========================================================
-- Step 2 : Update Gender for Male Employees
-- ==========================================================

UPDATE employee_payroll
SET gender = 'M'
WHERE name IN ('Bill', 'Charlie');


-- ==========================================================
-- Step 3 : Update Gender for Female Employees
-- ==========================================================

UPDATE employee_payroll
SET gender = 'F'
WHERE name = 'Terisa';


-- ==========================================================
-- Step 4 : Verify Updated Records
-- ==========================================================

SELECT *
FROM employee_payroll;