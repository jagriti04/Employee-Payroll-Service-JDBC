package com.bl.jdbc.employeepayrollservicejdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollDBService {
	public EmployeePayrollDBService() {

	}

	private Connection getConnection() throws SQLException {
		String jdbcURl = "jdbc:mysql://localhost:3306/emp_payroll_db?useSSL=false";
		String userName = "root";
		String password = "treadwill04";
		Connection con;
		System.out.println("Connecting to db:" + jdbcURl);
		con = DriverManager.getConnection(jdbcURl, userName, password);
		System.out.println("Connection is successful!!" + con);
		return con;
	}

	public List<EmployeePayrollData> readData() {
		String sql = "SELECT * FROM employee_payroll;";
		List<EmployeePayrollData> empList = new ArrayList<>();
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double salary = resultSet.getDouble("salary");
				LocalDate startDate = resultSet.getDate("start").toLocalDate();
				empList.add(new EmployeePayrollData(id, name, salary, startDate));
			}
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

}
