package com.nichesoft.test;

import com.nichesoft.bean.jpa.UserEntity;

public class UserEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public UserEntity newUserEntity() {

		Integer userId = mockValues.nextInteger();

		UserEntity userEntity = new UserEntity();
		userEntity.setUserId(userId);
		return userEntity;
	}
	
}
