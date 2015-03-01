package com.nichesoft.test;

import com.nichesoft.bean.RolePermission;

public class RolePermissionFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public RolePermission newRolePermission() {

		Integer rolePermissionId = mockValues.nextInteger();

		RolePermission rolePermission = new RolePermission();
		rolePermission.setRolePermissionId(rolePermissionId);
		return rolePermission;
	}
	
}
