package com.nichesoft.test;

import com.nichesoft.bean.Project;

public class ProjectFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Project newProject() {

		Integer projectId = mockValues.nextInteger();

		Project project = new Project();
		project.setProjectId(projectId);
		return project;
	}
	
}
