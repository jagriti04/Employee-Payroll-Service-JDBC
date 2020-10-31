package com.bl.jdbc.employeepayrollservicejdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {
	private PreparedStatement employeePayrollDataStatement;
	private static EmployeePayrollDBService employeePayrollDBService;
	
	public EmployeePayrollDBService() {

	}
	
	public static EmployeePayrollDBService getInstance() {
		if (employeePayrollDBService == null) {
			employeePayrollDBService = new EmployeePayrollDBService();
		}
		return employeePayrollDBService;
	}

	private Connection getConnection() throws SQLException {
		String jdbcURl = "jdbc:mysql://localhost:3306/emp_payroll_db?useSSL=false";
		String userName = "root";
		String password = "treadwill04";
		Connection con;
		con = DriverManager.getConnection(jdbcURl, userName, password);
		return con;
	}

	public List<EmployeePayrollData> readData() {
		String sql = "SELECT * FROM employee_payroll;";
		List<EmployeePayrollData> empList = new ArrayList<>();
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			empList = this.getEmployeePayrollData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empList;
	}

	public int updateEmployeeData(String name, double salary) {
		return this.updateEmployeeDataUsingStatement(name, salary);
	}

	private int updateEmployeeDataUsingStatement(String name, double salary) {
		String sql = String.format("update employee_payroll set salary = %.2f where name = '%s';", salary, name);
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) { 
			e.printStackTrace();
		}
		return 0;
	}

	public List<EmployeePayrollData> getEmployeePayrollData(String name) {
		List<EmployeePayrollData> empPayrollDataList = null;
		if (this.employeePayrollDataStatement == null)
			this.prepareStatementForEmployeeData();
		try {
			employeePayrollDataStatement.setString(1, name);
			ResultSet resultSet = employeePayrollDataStatement.executeQuery();
			empPayrollDataList = this.getEmployeePayrollData(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empPayrollDataList;
	}

	private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
		List<EmployeePayrollData> empPayrollDataList = new ArrayList<>();
		try {
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double salary = resultSet.getDouble("salary");
				LocalDate startDate = resultSet.getDate("start").toLocalDate();
				empPayrollDataList.add(new EmployeePayrollData(id, name, salary, startDate));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return empPayrollDataList;
	}

	private void prepareStatementForEmployeeData() {
		try {
			Connection conn = this.getConnection();
			String sql = "SELECT * FROM employee_payroll where name = ?";
			employeePayrollDataStatement = conn.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<EmployeePayrollData> getEmployeePayrollDataWithStartDateInGivenRange(String startDate, String endDate){
		List<EmployeePayrollData> empPayrollDataList = new ArrayList<>();

		try {
			Connection connection = this.getConnection();
			String sql = String.format("select * from employee_payroll where start BETWEEN CAST('%s' AS DATE) and CAST('%s' AS DATE);", startDate, endDate);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			empPayrollDataList = getEmployeePayrollData(resultSet);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empPayrollDataList;
	}

	public double getEmployeePayrollDataAvgSalary(String gender) {
		double avgSalary = 0;
		try (Connection connection = this.getConnection()){
			String sql = String.format("Select avg(salary) from employee_payroll Where gender = '%s' Group by gender;", gender);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				avgSalary = resultSet.getInt("avg(salary)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return avgSalary;
	}


	public EmployeePayrollData addEmployeeToPayroll(String name, String gender, double salary, LocalDate startDate) {
		int employeeID = -1;
		EmployeePayrollData empPayrollData = null;
		String sql = String.format("INSERT INTO employee_payroll (name, gender, salary, start)" + 
					"VALUES ('%s', '%s', '%s', '%s')", name, gender, salary, Date.valueOf(startDate));
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if(rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next()) employeeID = resultSet.getInt(1);
				System.out.println(resultSet.getInt(1));
			}
			empPayrollData = new EmployeePayrollData(employeeID, name, salary, startDate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empPayrollData;
	}
}
