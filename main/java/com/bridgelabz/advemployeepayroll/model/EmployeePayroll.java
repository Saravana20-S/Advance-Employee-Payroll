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
    private double basicPay;
    private char gender;
    private String department;
    private double deductions;

    private double taxablePay;

    private double incomeTax;

    private double netPay;

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


    public double getBasicPay() {
        return basicPay;
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


    /**
     * Updates the employee's basic pay.
     *
     * @param basicPay updated basic pay
     */
    public void setBasicPay(double basicPay) {
        this.basicPay = basicPay;
    }


    @Override
    public String toString() {
        return "EmployeePayroll{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", basicPay=" + basicPay +
                ", startDate=" + startDate +
                '}';
    }
}