-- ==========================================================
-- UC9 : Extend Employee Payroll Table with Payroll Details
-- Description :
--   Add payroll-related columns to the employee_payroll table.
-- Database : PostgreSQL
-- ==========================================================

-- Switch to the payroll_service database before executing.
-- psql command:
-- \c payroll_service


-- ==========================================================
-- Step 1 : Add Basic Pay
-- ==========================================================

ALTER TABLE employee_payroll
ADD COLUMN basic_pay DECIMAL(10,2);


-- ==========================================================
-- Step 2 : Add Deductions
-- ==========================================================

ALTER TABLE employee_payroll
ADD COLUMN deductions DECIMAL(10,2);


-- ==========================================================
-- Step 3 : Add Taxable Pay
-- ==========================================================

ALTER TABLE employee_payroll
ADD COLUMN taxable_pay DECIMAL(10,2);


-- ==========================================================
-- Step 4 : Add Income Tax
-- ==========================================================

ALTER TABLE employee_payroll
ADD COLUMN income_tax DECIMAL(10,2);


-- ==========================================================
-- Step 5 : Add Net Pay
-- ==========================================================

ALTER TABLE employee_payroll
ADD COLUMN net_pay DECIMAL(10,2);


-- ==========================================================
-- Step 6 : Verify Table Structure
-- ==========================================================

SELECT *
FROM employee_payroll;