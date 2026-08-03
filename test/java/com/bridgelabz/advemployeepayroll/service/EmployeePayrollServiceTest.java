package com.bridgelabz.advemployeepayroll.service;

import com.bridgelabz.advemployeepayroll.model.EmployeePayroll;
import com.bridgelabz.advemployeepayroll.model.PayrollStatistics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Test class for EmployeePayrollService.
 */
class EmployeePayrollServiceTest {

    private final EmployeePayrollService payrollService =
            EmployeePayrollService.getInstance();

    /**
     * UC4 :
     * Update employee salary using PreparedStatement
     * and verify synchronization with database.
     */
    @Test
    void givenUpdatedSalary_WhenSalaryUpdated_ShouldSyncWithDatabase() {

        EmployeePayroll employee =
                payrollService.updateEmployeeSalary(
                        "Terisa",
                        3000000.00);

        Assertions.assertNotNull(employee);

        Assertions.assertEquals(
                3000000.00,
                employee.getBasicPay()
        );

        Assertions.assertTrue(
                payrollService.checkEmployeePayrollInSync(employee)
        );
    }

    @Test
    void givenPayrollData_WhenGroupedByGender_ShouldReturnStatistics() {

        List<PayrollStatistics> statistics =
                payrollService.getPayrollStatisticsByGender();

        Assertions.assertFalse(((List<?>) statistics).isEmpty());

        Assertions.assertEquals(2, statistics.size());
    }
}