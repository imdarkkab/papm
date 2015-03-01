package com.nichesoft.test;

import com.nichesoft.bean.Permission;

public class PermissionFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Permission newPermission() {

		Integer permissionId = mockValues.nextInteger();

		Permission permission = new Permission();
		permission.setPermissionId(permissionId);
		return permission;
	}
	
}
