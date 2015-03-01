package com.nichesoft.test;

import com.nichesoft.bean.User;

public class UserFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public User newUser() {

		Integer userId = mockValues.nextInteger();

		User user = new User();
		user.setUserId(userId);
		return user;
	}
	
}
