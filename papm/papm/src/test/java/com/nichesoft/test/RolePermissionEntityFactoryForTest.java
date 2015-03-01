package com.nichesoft.test;

import com.nichesoft.bean.jpa.RolePermissionEntity;

public class RolePermissionEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public RolePermissionEntity newRolePermissionEntity() {

		Integer rolePermissionId = mockValues.nextInteger();

		RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
		rolePermissionEntity.setRolePermissionId(rolePermissionId);
		return rolePermissionEntity;
	}
	
}
