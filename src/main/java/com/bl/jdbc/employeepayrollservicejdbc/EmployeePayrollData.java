package com.bl.jdbc.employeepayrollservicejdbc;

import java.time.LocalDate;
import java.util.Date;

public class EmployeePayrollData {
	private int empId;
	private String empName;
	private double empSalary;
	private Date startDate;
	
	public EmployeePayrollData(int id, String name, double salary) {
		this.empId = id;
		this.empName = name;
		this.empSalary = salary;
	}

	public EmployeePayrollData(int id, String name, double salary, LocalDate startDate2) {
		this(id, name, salary);
		this.startDate = startDate;
	}

	public String toString() {
		return "id=" + empId + ", name=" + empName + ", salary=" + empSalary;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public double getEmpSalary() {
		return empSalary;
	}

	public void setEmpSalary(double empSalary) {
		this.empSalary = empSalary;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}
