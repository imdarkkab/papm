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
import com.nichesoft.bean.UserPermission;
import com.nichesoft.bean.Permission;
import com.nichesoft.bean.User;
import com.nichesoft.test.UserPermissionFactoryForTest;
import com.nichesoft.test.PermissionFactoryForTest;
import com.nichesoft.test.UserFactoryForTest;

//--- Services 
import com.nichesoft.business.service.UserPermissionService;
import com.nichesoft.business.service.PermissionService;
import com.nichesoft.business.service.UserService;

//--- List Items 
import com.nichesoft.web.listitem.PermissionListItem;
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
public class UserPermissionControllerTest {
	
	@InjectMocks
	private UserPermissionController userPermissionController;
	@Mock
	private UserPermissionService userPermissionService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private PermissionService permissionService; // Injected by Spring
	@Mock
	private UserService userService; // Injected by Spring

	private UserPermissionFactoryForTest userPermissionFactoryForTest = new UserPermissionFactoryForTest();
	private PermissionFactoryForTest permissionFactoryForTest = new PermissionFactoryForTest();
	private UserFactoryForTest userFactoryForTest = new UserFactoryForTest();

	List<Permission> permissions = new ArrayList<Permission>();
	List<User> users = new ArrayList<User>();

	private void givenPopulateModel() {
		Permission permission1 = permissionFactoryForTest.newPermission();
		Permission permission2 = permissionFactoryForTest.newPermission();
		List<Permission> permissions = new ArrayList<Permission>();
		permissions.add(permission1);
		permissions.add(permission2);
		when(permissionService.findAll()).thenReturn(permissions);

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
		
		List<UserPermission> list = new ArrayList<UserPermission>();
		when(userPermissionService.findAll()).thenReturn(list);
		
		// When
		String viewName = userPermissionController.list(model);
		
		// Then
		assertEquals("userPermission/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = userPermissionController.formForCreate(model);
		
		// Then
		assertEquals("userPermission/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((UserPermission)modelMap.get("userPermission")).getUserPermissionId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/userPermission/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PermissionListItem> permissionListItems = (List<PermissionListItem>) modelMap.get("listOfPermissionItems");
		assertEquals(2, permissionListItems.size());
		
		@SuppressWarnings("unchecked")
		List<UserListItem> userListItems = (List<UserListItem>) modelMap.get("listOfUserItems");
		assertEquals(2, userListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		UserPermission userPermission = userPermissionFactoryForTest.newUserPermission();
		Integer userPermissionId = userPermission.getUserPermissionId();
		when(userPermissionService.findById(userPermissionId)).thenReturn(userPermission);
		
		// When
		String viewName = userPermissionController.formForUpdate(model, userPermissionId);
		
		// Then
		assertEquals("userPermission/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(userPermission, (UserPermission) modelMap.get("userPermission"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/userPermission/update", modelMap.get("saveAction"));
		
		List<UserListItem> userListItems = (List<UserListItem>) modelMap.get("listOfUserItems");
		assertEquals(2, userListItems.size());
		
		List<PermissionListItem> permissionListItems = (List<PermissionListItem>) modelMap.get("listOfPermissionItems");
		assertEquals(2, permissionListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		UserPermission userPermission = userPermissionFactoryForTest.newUserPermission();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		UserPermission userPermissionCreated = new UserPermission();
		when(userPermissionService.create(userPermission)).thenReturn(userPermissionCreated); 
		
		// When
		String viewName = userPermissionController.create(userPermission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/userPermission/form/"+userPermission.getUserPermissionId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(userPermissionCreated, (UserPermission) modelMap.get("userPermission"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		UserPermission userPermission = userPermissionFactoryForTest.newUserPermission();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = userPermissionController.create(userPermission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("userPermission/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(userPermission, (UserPermission) modelMap.get("userPermission"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/userPermission/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PermissionListItem> permissionListItems = (List<PermissionListItem>) modelMap.get("listOfPermissionItems");
		assertEquals(2, permissionListItems.size());
		
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

		UserPermission userPermission = userPermissionFactoryForTest.newUserPermission();
		
		Exception exception = new RuntimeException("test exception");
		when(userPermissionService.create(userPermission)).thenThrow(exception);
		
		// When
		String viewName = userPermissionController.create(userPermission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("userPermission/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(userPermission, (UserPermission) modelMap.get("userPermission"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/userPermission/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "userPermission.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<PermissionListItem> permissionListItems = (List<PermissionListItem>) modelMap.get("listOfPermissionItems");
		assertEquals(2, permissionListItems.size());
		
		@SuppressWarnings("unchecked")
		List<UserListItem> userListItems = (List<UserListItem>) modelMap.get("listOfUserItems");
		assertEquals(2, userListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		UserPermission userPermission = userPermissionFactoryForTest.newUserPermission();
		Integer userPermissionId = userPermission.getUserPermissionId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		UserPermission userPermissionSaved = new UserPermission();
		userPermissionSaved.setUserPermissionId(userPermissionId);
		when(userPermissionService.update(userPermission)).thenReturn(userPermissionSaved); 
		
		// When
		String viewName = userPermissionController.update(userPermission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/userPermission/form/"+userPermission.getUserPermissionId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(userPermissionSaved, (UserPermission) modelMap.get("userPermission"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		UserPermission userPermission = userPermissionFactoryForTest.newUserPermission();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = userPermissionController.update(userPermission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("userPermission/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(userPermission, (UserPermission) modelMap.get("userPermission"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/userPermission/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<UserListItem> userListItems = (List<UserListItem>) modelMap.get("listOfUserItems");
		assertEquals(2, userListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PermissionListItem> permissionListItems = (List<PermissionListItem>) modelMap.get("listOfPermissionItems");
		assertEquals(2, permissionListItems.size());
		
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

		UserPermission userPermission = userPermissionFactoryForTest.newUserPermission();
		
		Exception exception = new RuntimeException("test exception");
		when(userPermissionService.update(userPermission)).thenThrow(exception);
		
		// When
		String viewName = userPermissionController.update(userPermission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("userPermission/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(userPermission, (UserPermission) modelMap.get("userPermission"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/userPermission/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "userPermission.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<UserListItem> userListItems = (List<UserListItem>) modelMap.get("listOfUserItems");
		assertEquals(2, userListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PermissionListItem> permissionListItems = (List<PermissionListItem>) modelMap.get("listOfPermissionItems");
		assertEquals(2, permissionListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		UserPermission userPermission = userPermissionFactoryForTest.newUserPermission();
		Integer userPermissionId = userPermission.getUserPermissionId();
		
		// When
		String viewName = userPermissionController.delete(redirectAttributes, userPermissionId);
		
		// Then
		verify(userPermissionService).delete(userPermissionId);
		assertEquals("redirect:/userPermission", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		UserPermission userPermission = userPermissionFactoryForTest.newUserPermission();
		Integer userPermissionId = userPermission.getUserPermissionId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(userPermissionService).delete(userPermissionId);
		
		// When
		String viewName = userPermissionController.delete(redirectAttributes, userPermissionId);
		
		// Then
		verify(userPermissionService).delete(userPermissionId);
		assertEquals("redirect:/userPermission", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "userPermission.error.delete", exception);
	}
	
	
}
