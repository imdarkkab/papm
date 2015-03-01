/*
 * Created on 27 �.�. 2558 ( Time 16:44:19 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.nichesoft.bean.Project;
import com.nichesoft.bean.jpa.ProjectEntity;
import java.util.Date;
import java.util.List;
import com.nichesoft.business.service.mapping.ProjectServiceMapper;
import com.nichesoft.data.repository.jpa.ProjectJpaRepository;
import com.nichesoft.test.ProjectFactoryForTest;
import com.nichesoft.test.ProjectEntityFactoryForTest;
import com.nichesoft.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of ProjectService
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceImplTest {

	@InjectMocks
	private ProjectServiceImpl projectService;
	@Mock
	private ProjectJpaRepository projectJpaRepository;
	@Mock
	private ProjectServiceMapper projectServiceMapper;
	
	private ProjectFactoryForTest projectFactoryForTest = new ProjectFactoryForTest();

	private ProjectEntityFactoryForTest projectEntityFactoryForTest = new ProjectEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer projectId = mockValues.nextInteger();
		
		ProjectEntity projectEntity = projectJpaRepository.findOne(projectId);
		
		Project project = projectFactoryForTest.newProject();
		when(projectServiceMapper.mapProjectEntityToProject(projectEntity)).thenReturn(project);

		// When
		Project projectFound = projectService.findById(projectId);

		// Then
		assertEquals(project.getProjectId(),projectFound.getProjectId());
	}

	@Test
	public void findAll() {
		// Given
		List<ProjectEntity> projectEntitys = new ArrayList<ProjectEntity>();
		ProjectEntity projectEntity1 = projectEntityFactoryForTest.newProjectEntity();
		projectEntitys.add(projectEntity1);
		ProjectEntity projectEntity2 = projectEntityFactoryForTest.newProjectEntity();
		projectEntitys.add(projectEntity2);
		when(projectJpaRepository.findAll()).thenReturn(projectEntitys);
		
		Project project1 = projectFactoryForTest.newProject();
		when(projectServiceMapper.mapProjectEntityToProject(projectEntity1)).thenReturn(project1);
		Project project2 = projectFactoryForTest.newProject();
		when(projectServiceMapper.mapProjectEntityToProject(projectEntity2)).thenReturn(project2);

		// When
		List<Project> projectsFounds = projectService.findAll();

		// Then
		assertTrue(project1 == projectsFounds.get(0));
		assertTrue(project2 == projectsFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Project project = projectFactoryForTest.newProject();

		ProjectEntity projectEntity = projectEntityFactoryForTest.newProjectEntity();
		when(projectJpaRepository.findOne(project.getProjectId())).thenReturn(null);
		
		projectEntity = new ProjectEntity();
		projectServiceMapper.mapProjectToProjectEntity(project, projectEntity);
		ProjectEntity projectEntitySaved = projectJpaRepository.save(projectEntity);
		
		Project projectSaved = projectFactoryForTest.newProject();
		when(projectServiceMapper.mapProjectEntityToProject(projectEntitySaved)).thenReturn(projectSaved);

		// When
		Project projectResult = projectService.create(project);

		// Then
		assertTrue(projectResult == projectSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Project project = projectFactoryForTest.newProject();

		ProjectEntity projectEntity = projectEntityFactoryForTest.newProjectEntity();
		when(projectJpaRepository.findOne(project.getProjectId())).thenReturn(projectEntity);

		// When
		Exception exception = null;
		try {
			projectService.create(project);
		} catch(Exception e) {
			exception = e;
		}

		// Then
		assertTrue(exception instanceof IllegalStateException);
		assertEquals("already.exists", exception.getMessage());
	}

	@Test
	public void update() {
		// Given
		Project project = projectFactoryForTest.newProject();

		ProjectEntity projectEntity = projectEntityFactoryForTest.newProjectEntity();
		when(projectJpaRepository.findOne(project.getProjectId())).thenReturn(projectEntity);
		
		ProjectEntity projectEntitySaved = projectEntityFactoryForTest.newProjectEntity();
		when(projectJpaRepository.save(projectEntity)).thenReturn(projectEntitySaved);
		
		Project projectSaved = projectFactoryForTest.newProject();
		when(projectServiceMapper.mapProjectEntityToProject(projectEntitySaved)).thenReturn(projectSaved);

		// When
		Project projectResult = projectService.update(project);

		// Then
		verify(projectServiceMapper).mapProjectToProjectEntity(project, projectEntity);
		assertTrue(projectResult == projectSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer projectId = mockValues.nextInteger();

		// When
		projectService.delete(projectId);

		// Then
		verify(projectJpaRepository).delete(projectId);
		
	}

}
