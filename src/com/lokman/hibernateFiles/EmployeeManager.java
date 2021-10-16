package com.lokman.hibernateFiles;

public class EmployeeManager {
	public static void main(String[] args) {
		Employee e1 = new Employee();
		e1.setFirstName("sahabur");
		e1.setLastName("alam");
		e1.setSalary(50000);

		EmployeeDAO.getInstance().save(e1);
	}

}
