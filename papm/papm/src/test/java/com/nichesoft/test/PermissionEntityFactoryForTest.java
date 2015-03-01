package com.nichesoft.test;

import com.nichesoft.bean.jpa.PermissionEntity;

public class PermissionEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PermissionEntity newPermissionEntity() {

		Integer permissionId = mockValues.nextInteger();

		PermissionEntity permissionEntity = new PermissionEntity();
		permissionEntity.setPermissionId(permissionId);
		return permissionEntity;
	}
	
}
