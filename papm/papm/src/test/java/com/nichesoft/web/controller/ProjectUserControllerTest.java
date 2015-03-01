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
import com.nichesoft.bean.ProjectUser;
import com.nichesoft.bean.Project;
import com.nichesoft.bean.User;
import com.nichesoft.test.ProjectUserFactoryForTest;
import com.nichesoft.test.ProjectFactoryForTest;
import com.nichesoft.test.UserFactoryForTest;

//--- Services 
import com.nichesoft.business.service.ProjectUserService;
import com.nichesoft.business.service.ProjectService;
import com.nichesoft.business.service.UserService;

//--- List Items 
import com.nichesoft.web.listitem.ProjectListItem;
import com.nichesoft.web.listitem.UserListItem;

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
public class ProjectUserControllerTest {
	
	@InjectMocks
	private ProjectUserController projectUserController;
	@Mock
	private ProjectUserService projectUserService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private ProjectService projectService; // Injected by Spring
	@Mock
	private UserService userService; // Injected by Spring

	private ProjectUserFactoryForTest projectUserFactoryForTest = new ProjectUserFactoryForTest();
	private ProjectFactoryForTest projectFactoryForTest = new ProjectFactoryForTest();
	private UserFactoryForTest userFactoryForTest = new UserFactoryForTest();

	List<Project> projects = new ArrayList<Project>();
	List<User> users = new ArrayList<User>();

	private void givenPopulateModel() {
		Project project1 = projectFactoryForTest.newProject();
		Project project2 = projectFactoryForTest.newProject();
		List<Project> projects = new ArrayList<Project>();
		projects.add(project1);
		projects.add(project2);
		when(projectService.findAll()).thenReturn(projects);

		User user1 = userFactoryForTest.newUser();
		User user2 = userFactoryForTest.newUser();
		List<User> users = new ArrayList<User>();
		users.add(user1);
		users.add(user2);
		when(userService.findAll()).thenReturn(users);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<ProjectUser> list = new ArrayList<ProjectUser>();
		when(projectUserService.findAll()).thenReturn(list);
		
		// When
		String viewName = projectUserController.list(model);
		
		// Then
		assertEquals("projectUser/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = projectUserController.formForCreate(model);
		
		// Then
		assertEquals("projectUser/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((ProjectUser)modelMap.get("projectUser")).getProjectUserId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/projectUser/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ProjectListItem> projectListItems = (List<ProjectListItem>) modelMap.get("listOfProjectItems");
		assertEquals(2, projectListItems.size());
		
		@SuppressWarnings("unchecked")
		List<UserListItem> userListItems = (List<UserListItem>) modelMap.get("listOfUserItems");
		assertEquals(2, userListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ProjectUser projectUser = projectUserFactoryForTest.newProjectUser();
		Integer projectUserId = projectUser.getProjectUserId();
		when(projectUserService.findById(projectUserId)).thenReturn(projectUser);
		
		// When
		String viewName = projectUserController.formForUpdate(model, projectUserId);
		
		// Then
		assertEquals("projectUser/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(projectUser, (ProjectUser) modelMap.get("projectUser"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/projectUser/update", modelMap.get("saveAction"));
		
		List<ProjectListItem> projectListItems = (List<ProjectListItem>) modelMap.get("listOfProjectItems");
		assertEquals(2, projectListItems.size());
		
		List<UserListItem> userListItems = (List<UserListItem>) modelMap.get("listOfUserItems");
		assertEquals(2, userListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		ProjectUser projectUser = projectUserFactoryForTest.newProjectUser();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		ProjectUser projectUserCreated = new ProjectUser();
		when(projectUserService.create(projectUser)).thenReturn(projectUserCreated); 
		
		// When
		String viewName = projectUserController.create(projectUser, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/projectUser/form/"+projectUser.getProjectUserId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(projectUserCreated, (ProjectUser) modelMap.get("projectUser"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ProjectUser projectUser = projectUserFactoryForTest.newProjectUser();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = projectUserController.create(projectUser, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("projectUser/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(projectUser, (ProjectUser) modelMap.get("projectUser"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/projectUser/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ProjectListItem> projectListItems = (List<ProjectListItem>) modelMap.get("listOfProjectItems");
		assertEquals(2, projectListItems.size());
		
		@SuppressWarnings("unchecked")
		List<UserListItem> userListItems = (List<UserListItem>) modelMap.get("listOfUserItems");
		assertEquals(2, userListItems.size());
		
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

		ProjectUser projectUser = projectUserFactoryForTest.newProjectUser();
		
		Exception exception = new RuntimeException("test exception");
		when(projectUserService.create(projectUser)).thenThrow(exception);
		
		// When
		String viewName = projectUserController.create(projectUser, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("projectUser/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(projectUser, (ProjectUser) modelMap.get("projectUser"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/projectUser/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "projectUser.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<ProjectListItem> projectListItems = (List<ProjectListItem>) modelMap.get("listOfProjectItems");
		assertEquals(2, projectListItems.size());
		
		@SuppressWarnings("unchecked")
		List<UserListItem> userListItems = (List<UserListItem>) modelMap.get("listOfUserItems");
		assertEquals(2, userListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		ProjectUser projectUser = projectUserFactoryForTest.newProjectUser();
		Integer projectUserId = projectUser.getProjectUserId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		ProjectUser projectUserSaved = new ProjectUser();
		projectUserSaved.setProjectUserId(projectUserId);
		when(projectUserService.update(projectUser)).thenReturn(projectUserSaved); 
		
		// When
		String viewName = projectUserController.update(projectUser, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/projectUser/form/"+projectUser.getProjectUserId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(projectUserSaved, (ProjectUser) modelMap.get("projectUser"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ProjectUser projectUser = projectUserFactoryForTest.newProjectUser();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = projectUserController.update(projectUser, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("projectUser/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(projectUser, (ProjectUser) modelMap.get("projectUser"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/projectUser/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ProjectListItem> projectListItems = (List<ProjectListItem>) modelMap.get("listOfProjectItems");
		assertEquals(2, projectListItems.size());
		
		@SuppressWarnings("unchecked")
		List<UserListItem> userListItems = (List<UserListItem>) modelMap.get("listOfUserItems");
		assertEquals(2, userListItems.size());
		
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

		ProjectUser projectUser = projectUserFactoryForTest.newProjectUser();
		
		Exception exception = new RuntimeException("test exception");
		when(projectUserService.update(projectUser)).thenThrow(exception);
		
		// When
		String viewName = projectUserController.update(projectUser, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("projectUser/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(projectUser, (ProjectUser) modelMap.get("projectUser"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/projectUser/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "projectUser.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<ProjectListItem> projectListItems = (List<ProjectListItem>) modelMap.get("listOfProjectItems");
		assertEquals(2, projectListItems.size());
		
		@SuppressWarnings("unchecked")
		List<UserListItem> userListItems = (List<UserListItem>) modelMap.get("listOfUserItems");
		assertEquals(2, userListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		ProjectUser projectUser = projectUserFactoryForTest.newProjectUser();
		Integer projectUserId = projectUser.getProjectUserId();
		
		// When
		String viewName = projectUserController.delete(redirectAttributes, projectUserId);
		
		// Then
		verify(projectUserService).delete(projectUserId);
		assertEquals("redirect:/projectUser", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		ProjectUser projectUser = projectUserFactoryForTest.newProjectUser();
		Integer projectUserId = projectUser.getProjectUserId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(projectUserService).delete(projectUserId);
		
		// When
		String viewName = projectUserController.delete(redirectAttributes, projectUserId);
		
		// Then
		verify(projectUserService).delete(projectUserId);
		assertEquals("redirect:/projectUser", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "projectUser.error.delete", exception);
	}
	
	
}
