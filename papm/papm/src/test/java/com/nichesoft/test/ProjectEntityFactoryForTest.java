package com.nichesoft.test;

import com.nichesoft.bean.jpa.ProjectEntity;

public class ProjectEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ProjectEntity newProjectEntity() {

		Integer projectId = mockValues.nextInteger();

		ProjectEntity projectEntity = new ProjectEntity();
		projectEntity.setProjectId(projectId);
		return projectEntity;
	}
	
}
