package com.bridgelabz.advemployeepayroll.model;

/**
 * Model class to store payroll statistics
 * for a particular gender.
 */
public class PayrollStatistics {

    private char gender;
    private double sum;
    private double average;
    private double minimum;
    private double maximum;
    private int count;

    public PayrollStatistics(char gender,
                             double sum,
                             double average,
                             double minimum,
                             double maximum,
                             int count) {

        this.gender = gender;
        this.sum = sum;
        this.average = average;
        this.minimum = minimum;
        this.maximum = maximum;
        this.count = count;
    }

    public char getGender() {
        return gender;
    }

    public double getSum() {
        return sum;
    }

    public double getAverage() {
        return average;
    }

    public double getMinimum() {
        return minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {

        return "PayrollStatistics{" +
                "gender=" + gender +
                ", sum=" + sum +
                ", average=" + average +
                ", minimum=" + minimum +
                ", maximum=" + maximum +
                ", count=" + count +
                '}';
    }
}