package com.nichesoft.test;

import com.nichesoft.bean.jpa.ProjectUserEntity;

public class ProjectUserEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ProjectUserEntity newProjectUserEntity() {

		Integer projectUserId = mockValues.nextInteger();

		ProjectUserEntity projectUserEntity = new ProjectUserEntity();
		projectUserEntity.setProjectUserId(projectUserId);
		return projectUserEntity;
	}
	
}
