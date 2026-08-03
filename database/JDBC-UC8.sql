CREATE TABLE payroll (
    payroll_id SERIAL PRIMARY KEY,
    employee_id INT UNIQUE NOT NULL,
    basic_pay DECIMAL(10,2) NOT NULL,
    deductions DECIMAL(10,2) NOT NULL,
    taxable_pay DECIMAL(10,2) NOT NULL,
    income_tax DECIMAL(10,2) NOT NULL,
    net_pay DECIMAL(10,2) NOT NULL,
    FOREIGN KEY(employee_id)
        REFERENCES employee(employee_id)
);