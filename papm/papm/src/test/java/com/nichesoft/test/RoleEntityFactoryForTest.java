package com.nichesoft.test;

import com.nichesoft.bean.jpa.RoleEntity;

public class RoleEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public RoleEntity newRoleEntity() {

		Integer roleId = mockValues.nextInteger();

		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setRoleId(roleId);
		return roleEntity;
	}
	
}
