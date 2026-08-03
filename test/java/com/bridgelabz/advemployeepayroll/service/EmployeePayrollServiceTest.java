package com.bridgelabz.advemployeepayroll.service;

import com.bridgelabz.advemployeepayroll.model.EmployeePayroll;
import com.bridgelabz.advemployeepayroll.model.PayrollStatistics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
    




    //UC10
    @Test
    void givenPayrollDatabase_WhenRetrieved_ShouldReturnEmployeeList() {

        List<EmployeePayroll> list =
                payrollService.getEmployeePayrollList();

        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    void givenNewSalary_WhenUpdated_ShouldSyncWithDatabase() {

        EmployeePayroll employee =
                payrollService.updateEmployeeSalary(
                        "Terisa",
                        3000000);

        Assertions.assertTrue(
                payrollService
                        .checkEmployeePayrollInSync(employee));
    }

    @Test
    void givenDateRange_WhenRetrieved_ShouldReturnEmployees() {

        List<EmployeePayroll> employees =
                payrollService.getEmployeesByDateRange(

                        LocalDate.of(2018,1,1),

                        LocalDate.now()

                );

        Assertions.assertFalse(
                employees.isEmpty());
    }


    @Test
    void givenEmployees_WhenStatisticsComputed_ShouldReturnGenderStatistics() {

        List<PayrollStatistics> statistics =
                payrollService
                        .getPayrollStatisticsByGender();

        Assertions.assertFalse(
                statistics.isEmpty());
    }

    @Test
    void givenNewEmployee_WhenAdded_ShouldSyncWithDatabase() {

        EmployeePayroll employee =
                payrollService.addEmployee(
                        "Arun",
                        'M',
                        5500000,
                        LocalDate.now(),
                        List.of(1, 2));

        Assertions.assertNotNull(employee);

        Assertions.assertEquals(
                2,
                employee.getDepartments().size());

        Assertions.assertTrue(
                payrollService.checkEmployeePayrollInSync(employee));
    }
}