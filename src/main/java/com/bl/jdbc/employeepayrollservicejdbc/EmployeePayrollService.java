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

	public void updateEmployeeSalary(String name, double salary) {
		int result = new EmployeePayrollDBService().updateEmployeeData(name, salary);
		if (result == 0)
			return; 
		EmployeePayrollData empPayrollData = this.getEmployeePayrollData(name);
		if (empPayrollData != null)
			empPayrollData.setEmpSalary(salary);
	}

	private EmployeePayrollData getEmployeePayrollData(String name) {
		return this.empPayrollList.stream().filter(empPayrollDataItem -> empPayrollDataItem.getEmpName().equals(name))
				.findFirst().orElse(null);
	}

	public boolean checkEmployeePayrollInSyncWithDB(String name) {
		List<EmployeePayrollData> empPayrollDataList = new EmployeePayrollDBService().getEmployeePayrollData(name);
		return empPayrollDataList.get(0).equals(getEmployeePayrollData(name));
	}

}
