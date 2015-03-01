package com.nichesoft.test;

import com.nichesoft.bean.Role;

public class RoleFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Role newRole() {

		Integer roleId = mockValues.nextInteger();

		Role role = new Role();
		role.setRoleId(roleId);
		return role;
	}
	
}
