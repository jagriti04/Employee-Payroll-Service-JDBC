package com.bl.jdbc.employeepayrollservicejdbc;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class EmployeePayrollTest {

	@Test
	public void givenEmployeePayrollInDB_whenRetrieved_shouldMatchEmpCount() {
		EmployeePayrollService empPayrollService = new EmployeePayrollService();
		List<EmployeePayrollData> empPayrollData = empPayrollService
				.readEmployeePayrollDataDB(EmployeePayrollService.IOService.DB_IO);
		Assert.assertEquals(3, empPayrollData.size());
	}
}
