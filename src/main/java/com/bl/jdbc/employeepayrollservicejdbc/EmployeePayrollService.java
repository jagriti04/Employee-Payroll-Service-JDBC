package com.bl.jdbc.employeepayrollservicejdbc;

import java.util.List;

public class EmployeePayrollService {
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}

	private List<EmployeePayrollData> empPayrollList;

	// Parameterized constructor
	public EmployeePayrollService(List<EmployeePayrollData> empPayrollList) {
		this.empPayrollList = empPayrollList;
	}
	
	public EmployeePayrollService() {
		
	}

	public List<EmployeePayrollData> readEmployeePayrollDataDB(IOService ioService) {
		if (ioService.equals(IOService.DB_IO)) {
			this.empPayrollList = new EmployeePayrollDBService().readData();
		}
		return this.empPayrollList;

	}
}
