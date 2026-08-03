package com.bridgelabz.advemployeepayroll.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Model class representing an Employee Payroll record.
 * This class contains employee information, payroll details,
 * and the departments to which the employee belongs.
 */
public class EmployeePayroll {

    // Employee Details
    private int id;
    private String name;
    private char gender;
    private LocalDate startDate;

    // Payroll Details
    private double basicPay;
    private double deductions;
    private double taxablePay;
    private double incomeTax;
    private double netPay;

    // Employee can belong to multiple departments
    private List<String> departments;

    /**
     * Parameterized constructor.
     *
     * @param id employee id
     * @param name employee name
     * @param basicPay employee basic pay
     * @param startDate joining date
     */
    public EmployeePayroll(int id,
                           String name,
                           double basicPay,
                           LocalDate startDate) {

        this.id = id;
        this.name = name;
        this.basicPay = basicPay;
        this.startDate = startDate;
        this.departments = new ArrayList<>();
    }

    // ==========================
    // Getters
    // ==========================

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public char getGender() {
        return gender;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public double getBasicPay() {
        return basicPay;
    }

    public double getDeductions() {
        return deductions;
    }

    public double getTaxablePay() {
        return taxablePay;
    }

    public double getIncomeTax() {
        return incomeTax;
    }

    public double getNetPay() {
        return netPay;
    }

    public List<String> getDepartments() {
        return departments;
    }

    // ==========================
    // Setters
    // ==========================

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setBasicPay(double basicPay) {
        this.basicPay = basicPay;
    }

    public void setDeductions(double deductions) {
        this.deductions = deductions;
    }

    public void setTaxablePay(double taxablePay) {
        this.taxablePay = taxablePay;
    }

    public void setIncomeTax(double incomeTax) {
        this.incomeTax = incomeTax;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }

    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }

    /**
     * Adds a department to the employee.
     *
     * @param department department name
     */
    public void addDepartment(String department) {
        this.departments.add(department);
    }

    @Override
    public String toString() {

        return "EmployeePayroll{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", startDate=" + startDate +
                ", basicPay=" + basicPay +
                ", deductions=" + deductions +
                ", taxablePay=" + taxablePay +
                ", incomeTax=" + incomeTax +
                ", netPay=" + netPay +
                ", departments=" + departments +
                '}';
    }
}