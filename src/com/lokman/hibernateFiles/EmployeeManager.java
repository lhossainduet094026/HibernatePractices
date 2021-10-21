package com.lokman.hibernateFiles;

import java.util.List;
import java.util.Map;

public class EmployeeManager {
	public static void main(String[] args) {

		// saving employee
		Employee e1 = new Employee();
		e1.setFirstName("sahabur");
		e1.setLastName("alam");
		e1.setSalary(50000);

		// EmployeeDAO.getInstance().save(e1);

		// read all employee

		List<Employee> allEmployee = EmployeeDAO.getInstance().findAll();
		for (Employee aEmployee : allEmployee) {
			System.out.println("First Name:" + aEmployee.getFirstName());
			System.out.println("Last Name:" + aEmployee.getLastName());
			System.out.println("Salary:" + aEmployee.getSalary());
			System.out.println("------------------------------------------");
		}

		// find employee by id

//		Employee employee = EmployeeDAO.getInstance().findById(7);
//		System.out.println(employee);

		// delete employee

		// EmployeeDAO.getInstance().delete(employee);

		// find sum of total salary

//		Long totalSalary = EmployeeDAO.getInstance().sumOfSalaries();
//		System.out.println("Total salary:" + totalSalary);

		List data = EmployeeDAO.getInstance().getAllEmployee();
		for (Object object : data) {
			Map employee = (Map) object;
			System.out.println("first name: " + employee.get("first_name"));
			System.out.println("salary: " + employee.get("salary"));

		}

	}

}
