-- ==========================================================
-- UC10 : Make Terisa Part of Sales and Marketing Department
-- Description :
--   Insert another record for Terisa to represent
--   a different department.
-- Database : PostgreSQL
-- ==========================================================

-- Switch to the payroll_service database before executing.
-- psql command:
-- \c payroll_service


-- ==========================================================
-- Step 1 : Update Existing Terisa Record
--           (Optional - If not already assigned)
-- ==========================================================

UPDATE employee_payroll
SET department = 'Sales'
WHERE name = 'Terisa';


-- ==========================================================
-- Step 2 : Insert Another Record for Terisa
--           in Marketing Department
-- ==========================================================

INSERT INTO employee_payroll
(
    name,
    salary,
    start_date,
    gender,
    phone,
    address,
    department,
    basic_pay,
    deductions,
    taxable_pay,
    income_tax,
    net_pay
)
VALUES
(
    'Terisa',
    200000.00,
    '2019-11-13',
    'F',
    '9876543210',
    'Bangalore',
    'Marketing',
    200000.00,
    10000.00,
    190000.00,
    25000.00,
    165000.00
);


-- ==========================================================
-- Step 3 : Verify the Data
-- ==========================================================

SELECT *
FROM employee_payroll
WHERE name = 'Terisa';