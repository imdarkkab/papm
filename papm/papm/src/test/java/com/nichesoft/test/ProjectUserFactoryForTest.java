package com.nichesoft.test;

import com.nichesoft.bean.ProjectUser;

public class ProjectUserFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ProjectUser newProjectUser() {

		Integer projectUserId = mockValues.nextInteger();

		ProjectUser projectUser = new ProjectUser();
		projectUser.setProjectUserId(projectUserId);
		return projectUser;
	}
	
}
