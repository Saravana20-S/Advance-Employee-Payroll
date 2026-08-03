package com.bridgelabz.advemployeepayroll.service;

import com.bridgelabz.advemployeepayroll.model.EmployeePayroll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class for EmployeePayrollService.
 */
class EmployeePayrollServiceTest {

    private final EmployeePayrollService payrollService =
            new EmployeePayrollService();

    @Test
    void givenNewSalary_WhenUpdated_ShouldSyncWithDatabase() {

        // Update Terisa's salary
        EmployeePayroll employee =
                payrollService.updateEmployeeSalary(
                        "Terisa",
                        3000000.00);

        // Verify update was successful
        Assertions.assertNotNull(employee);

        // Verify the object is synchronized with the database
        Assertions.assertTrue(
                payrollService.checkEmployeePayrollInSync(employee)
        );
    }
}