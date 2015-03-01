package com.nichesoft.test;

import com.nichesoft.bean.jpa.EmployeeEntity;

public class EmployeeEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public EmployeeEntity newEmployeeEntity() {

		Integer employeeId = mockValues.nextInteger();

		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setEmployeeId(employeeId);
		return employeeEntity;
	}
	
}
