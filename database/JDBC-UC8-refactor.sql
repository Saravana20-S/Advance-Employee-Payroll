ALTER TABLE payroll
DROP CONSTRAINT payroll_employee_id_fkey;

ALTER TABLE payroll
ADD CONSTRAINT payroll_employee_id_fkey
FOREIGN KEY (employee_id)
REFERENCES employee(employee_id)
ON DELETE CASCADE;


ALTER TABLE employee_department
DROP CONSTRAINT employee_department_employee_id_fkey;

ALTER TABLE employee_department
ADD CONSTRAINT employee_department_employee_id_fkey
FOREIGN KEY (employee_id)
REFERENCES employee(employee_id)
ON DELETE CASCADE;