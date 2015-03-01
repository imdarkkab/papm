package com.nichesoft.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//--- Entities
import com.nichesoft.bean.Project;
import com.nichesoft.test.ProjectFactoryForTest;

//--- Services 
import com.nichesoft.business.service.ProjectService;


import com.nichesoft.web.common.Message;
import com.nichesoft.web.common.MessageHelper;
import com.nichesoft.web.common.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class ProjectControllerTest {
	
	@InjectMocks
	private ProjectController projectController;
	@Mock
	private ProjectService projectService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private ProjectFactoryForTest projectFactoryForTest = new ProjectFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Project> list = new ArrayList<Project>();
		when(projectService.findAll()).thenReturn(list);
		
		// When
		String viewName = projectController.list(model);
		
		// Then
		assertEquals("project/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = projectController.formForCreate(model);
		
		// Then
		assertEquals("project/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Project)modelMap.get("project")).getProjectId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/project/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Project project = projectFactoryForTest.newProject();
		Integer projectId = project.getProjectId();
		when(projectService.findById(projectId)).thenReturn(project);
		
		// When
		String viewName = projectController.formForUpdate(model, projectId);
		
		// Then
		assertEquals("project/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(project, (Project) modelMap.get("project"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/project/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Project project = projectFactoryForTest.newProject();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Project projectCreated = new Project();
		when(projectService.create(project)).thenReturn(projectCreated); 
		
		// When
		String viewName = projectController.create(project, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/project/form/"+project.getProjectId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(projectCreated, (Project) modelMap.get("project"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Project project = projectFactoryForTest.newProject();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = projectController.create(project, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("project/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(project, (Project) modelMap.get("project"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/project/create", modelMap.get("saveAction"));
		
	}

	@Test
	public void createException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		Project project = projectFactoryForTest.newProject();
		
		Exception exception = new RuntimeException("test exception");
		when(projectService.create(project)).thenThrow(exception);
		
		// When
		String viewName = projectController.create(project, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("project/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(project, (Project) modelMap.get("project"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/project/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "project.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Project project = projectFactoryForTest.newProject();
		Integer projectId = project.getProjectId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Project projectSaved = new Project();
		projectSaved.setProjectId(projectId);
		when(projectService.update(project)).thenReturn(projectSaved); 
		
		// When
		String viewName = projectController.update(project, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/project/form/"+project.getProjectId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(projectSaved, (Project) modelMap.get("project"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Project project = projectFactoryForTest.newProject();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = projectController.update(project, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("project/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(project, (Project) modelMap.get("project"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/project/update", modelMap.get("saveAction"));
		
	}

	@Test
	public void updateException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		Project project = projectFactoryForTest.newProject();
		
		Exception exception = new RuntimeException("test exception");
		when(projectService.update(project)).thenThrow(exception);
		
		// When
		String viewName = projectController.update(project, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("project/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(project, (Project) modelMap.get("project"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/project/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "project.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Project project = projectFactoryForTest.newProject();
		Integer projectId = project.getProjectId();
		
		// When
		String viewName = projectController.delete(redirectAttributes, projectId);
		
		// Then
		verify(projectService).delete(projectId);
		assertEquals("redirect:/project", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Project project = projectFactoryForTest.newProject();
		Integer projectId = project.getProjectId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(projectService).delete(projectId);
		
		// When
		String viewName = projectController.delete(redirectAttributes, projectId);
		
		// Then
		verify(projectService).delete(projectId);
		assertEquals("redirect:/project", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "project.error.delete", exception);
	}
	
	
}
