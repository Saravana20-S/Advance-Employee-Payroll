package com.bridgelabz.advemployeepayroll.model;

import java.time.LocalDate;

/**
 * Model class representing an Employee Payroll record.
 */
public class EmployeePayroll {

    private int id;
    private String name;
    private double salary;
    private LocalDate startDate;

    /**
     * Parameterized constructor.
     */
    public EmployeePayroll(int id,
                           String name,
                           double salary,
                           LocalDate startDate) {

        this.id = id;
        this.name = name;
        this.salary = salary;
        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {

        return "EmployeePayroll{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", startDate=" + startDate +
                '}';
    }
}