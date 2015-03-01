package com.nichesoft.test;

import com.nichesoft.bean.UserPermission;

public class UserPermissionFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public UserPermission newUserPermission() {

		Integer userPermissionId = mockValues.nextInteger();

		UserPermission userPermission = new UserPermission();
		userPermission.setUserPermissionId(userPermissionId);
		return userPermission;
	}
	
}
