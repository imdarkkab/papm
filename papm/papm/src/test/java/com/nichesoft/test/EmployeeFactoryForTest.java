package com.nichesoft.test;

import com.nichesoft.bean.Employee;

public class EmployeeFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Employee newEmployee() {

		Integer employeeId = mockValues.nextInteger();

		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		return employee;
	}
	
}
