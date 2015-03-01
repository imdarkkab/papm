package com.nichesoft.test;

import com.nichesoft.bean.jpa.UserPermissionEntity;

public class UserPermissionEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public UserPermissionEntity newUserPermissionEntity() {

		Integer userPermissionId = mockValues.nextInteger();

		UserPermissionEntity userPermissionEntity = new UserPermissionEntity();
		userPermissionEntity.setUserPermissionId(userPermissionId);
		return userPermissionEntity;
	}
	
}
