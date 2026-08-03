-- ==========================================================
-- UC11 : Implement Normalized Payroll Database
-- Description :
--   Create normalized tables based on the ER Diagram.
-- Database : PostgreSQL
-- ==========================================================

-- Connect to the payroll_service database before execution.
-- \c payroll_service


-- ==========================================================
-- Drop Existing Tables (Optional)
-- ==========================================================

DROP TABLE IF EXISTS employee_department CASCADE;
DROP TABLE IF EXISTS payroll CASCADE;
DROP TABLE IF EXISTS department CASCADE;
DROP TABLE IF EXISTS employee CASCADE;


-- ==========================================================
-- Employee Table
-- ==========================================================

CREATE TABLE employee
(
    employee_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender CHAR(1),
    phone VARCHAR(15),
    address VARCHAR(255) DEFAULT 'Not Available',
    start_date DATE NOT NULL
);


-- ==========================================================
-- Payroll Table
-- ==========================================================

CREATE TABLE payroll
(
    payroll_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,

    employee_id INT NOT NULL UNIQUE,

    basic_pay DECIMAL(10,2) NOT NULL,
    deductions DECIMAL(10,2) NOT NULL,
    taxable_pay DECIMAL(10,2) NOT NULL,
    income_tax DECIMAL(10,2) NOT NULL,
    net_pay DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_employee
        FOREIGN KEY(employee_id)
        REFERENCES employee(employee_id)
        ON DELETE CASCADE
);


-- ==========================================================
-- Department Table
-- ==========================================================

CREATE TABLE department
(
    department_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    department_name VARCHAR(100) UNIQUE NOT NULL
);


-- ==========================================================
-- Employee Department (Many-to-Many)
-- ==========================================================

CREATE TABLE employee_department
(
    employee_id INT NOT NULL,
    department_id INT NOT NULL,

    PRIMARY KEY(employee_id, department_id),

    CONSTRAINT fk_emp
        FOREIGN KEY(employee_id)
        REFERENCES employee(employee_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_dept
        FOREIGN KEY(department_id)
        REFERENCES department(department_id)
        ON DELETE CASCADE
);


-- ==========================================================
-- Verify Tables
-- ==========================================================

\dt