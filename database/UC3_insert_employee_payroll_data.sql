-- ==========================================================
-- UC3 : Insert Employee Payroll Data
-- Description : Insert sample employee records into the
--               employee_payroll table.
-- Database : PostgreSQL
-- ==========================================================

-- Switch to the payroll_service database before executing.
-- psql command:
-- \c payroll_service

INSERT INTO employee_payroll (name, salary, start_date)
VALUES
('Bill', 100000.00, '2018-01-03'),
('Terisa', 200000.00, '2019-11-13'),
('Charlie', 300000.00, '2020-05-21');