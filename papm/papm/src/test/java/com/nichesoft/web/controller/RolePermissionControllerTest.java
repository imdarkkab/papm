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
import com.nichesoft.bean.RolePermission;
import com.nichesoft.bean.Permission;
import com.nichesoft.bean.Role;
import com.nichesoft.test.RolePermissionFactoryForTest;
import com.nichesoft.test.PermissionFactoryForTest;
import com.nichesoft.test.RoleFactoryForTest;

//--- Services 
import com.nichesoft.business.service.RolePermissionService;
import com.nichesoft.business.service.PermissionService;
import com.nichesoft.business.service.RoleService;

//--- List Items 
import com.nichesoft.web.listitem.PermissionListItem;
import com.nichesoft.web.listitem.RoleListItem;

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
public class RolePermissionControllerTest {
	
	@InjectMocks
	private RolePermissionController rolePermissionController;
	@Mock
	private RolePermissionService rolePermissionService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private PermissionService permissionService; // Injected by Spring
	@Mock
	private RoleService roleService; // Injected by Spring

	private RolePermissionFactoryForTest rolePermissionFactoryForTest = new RolePermissionFactoryForTest();
	private PermissionFactoryForTest permissionFactoryForTest = new PermissionFactoryForTest();
	private RoleFactoryForTest roleFactoryForTest = new RoleFactoryForTest();

	List<Permission> permissions = new ArrayList<Permission>();
	List<Role> roles = new ArrayList<Role>();

	private void givenPopulateModel() {
		Permission permission1 = permissionFactoryForTest.newPermission();
		Permission permission2 = permissionFactoryForTest.newPermission();
		List<Permission> permissions = new ArrayList<Permission>();
		permissions.add(permission1);
		permissions.add(permission2);
		when(permissionService.findAll()).thenReturn(permissions);

		Role role1 = roleFactoryForTest.newRole();
		Role role2 = roleFactoryForTest.newRole();
		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		roles.add(role2);
		when(roleService.findAll()).thenReturn(roles);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<RolePermission> list = new ArrayList<RolePermission>();
		when(rolePermissionService.findAll()).thenReturn(list);
		
		// When
		String viewName = rolePermissionController.list(model);
		
		// Then
		assertEquals("rolePermission/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = rolePermissionController.formForCreate(model);
		
		// Then
		assertEquals("rolePermission/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((RolePermission)modelMap.get("rolePermission")).getRolePermissionId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/rolePermission/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PermissionListItem> permissionListItems = (List<PermissionListItem>) modelMap.get("listOfPermissionItems");
		assertEquals(2, permissionListItems.size());
		
		@SuppressWarnings("unchecked")
		List<RoleListItem> roleListItems = (List<RoleListItem>) modelMap.get("listOfRoleItems");
		assertEquals(2, roleListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RolePermission rolePermission = rolePermissionFactoryForTest.newRolePermission();
		Integer rolePermissionId = rolePermission.getRolePermissionId();
		when(rolePermissionService.findById(rolePermissionId)).thenReturn(rolePermission);
		
		// When
		String viewName = rolePermissionController.formForUpdate(model, rolePermissionId);
		
		// Then
		assertEquals("rolePermission/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(rolePermission, (RolePermission) modelMap.get("rolePermission"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/rolePermission/update", modelMap.get("saveAction"));
		
		List<RoleListItem> roleListItems = (List<RoleListItem>) modelMap.get("listOfRoleItems");
		assertEquals(2, roleListItems.size());
		
		List<PermissionListItem> permissionListItems = (List<PermissionListItem>) modelMap.get("listOfPermissionItems");
		assertEquals(2, permissionListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		RolePermission rolePermission = rolePermissionFactoryForTest.newRolePermission();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		RolePermission rolePermissionCreated = new RolePermission();
		when(rolePermissionService.create(rolePermission)).thenReturn(rolePermissionCreated); 
		
		// When
		String viewName = rolePermissionController.create(rolePermission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/rolePermission/form/"+rolePermission.getRolePermissionId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(rolePermissionCreated, (RolePermission) modelMap.get("rolePermission"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RolePermission rolePermission = rolePermissionFactoryForTest.newRolePermission();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = rolePermissionController.create(rolePermission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("rolePermission/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(rolePermission, (RolePermission) modelMap.get("rolePermission"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/rolePermission/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PermissionListItem> permissionListItems = (List<PermissionListItem>) modelMap.get("listOfPermissionItems");
		assertEquals(2, permissionListItems.size());
		
		@SuppressWarnings("unchecked")
		List<RoleListItem> roleListItems = (List<RoleListItem>) modelMap.get("listOfRoleItems");
		assertEquals(2, roleListItems.size());
		
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

		RolePermission rolePermission = rolePermissionFactoryForTest.newRolePermission();
		
		Exception exception = new RuntimeException("test exception");
		when(rolePermissionService.create(rolePermission)).thenThrow(exception);
		
		// When
		String viewName = rolePermissionController.create(rolePermission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("rolePermission/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(rolePermission, (RolePermission) modelMap.get("rolePermission"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/rolePermission/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "rolePermission.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<PermissionListItem> permissionListItems = (List<PermissionListItem>) modelMap.get("listOfPermissionItems");
		assertEquals(2, permissionListItems.size());
		
		@SuppressWarnings("unchecked")
		List<RoleListItem> roleListItems = (List<RoleListItem>) modelMap.get("listOfRoleItems");
		assertEquals(2, roleListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		RolePermission rolePermission = rolePermissionFactoryForTest.newRolePermission();
		Integer rolePermissionId = rolePermission.getRolePermissionId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		RolePermission rolePermissionSaved = new RolePermission();
		rolePermissionSaved.setRolePermissionId(rolePermissionId);
		when(rolePermissionService.update(rolePermission)).thenReturn(rolePermissionSaved); 
		
		// When
		String viewName = rolePermissionController.update(rolePermission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/rolePermission/form/"+rolePermission.getRolePermissionId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(rolePermissionSaved, (RolePermission) modelMap.get("rolePermission"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RolePermission rolePermission = rolePermissionFactoryForTest.newRolePermission();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = rolePermissionController.update(rolePermission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("rolePermission/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(rolePermission, (RolePermission) modelMap.get("rolePermission"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/rolePermission/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<RoleListItem> roleListItems = (List<RoleListItem>) modelMap.get("listOfRoleItems");
		assertEquals(2, roleListItems.size());
		
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

		RolePermission rolePermission = rolePermissionFactoryForTest.newRolePermission();
		
		Exception exception = new RuntimeException("test exception");
		when(rolePermissionService.update(rolePermission)).thenThrow(exception);
		
		// When
		String viewName = rolePermissionController.update(rolePermission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("rolePermission/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(rolePermission, (RolePermission) modelMap.get("rolePermission"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/rolePermission/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "rolePermission.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<RoleListItem> roleListItems = (List<RoleListItem>) modelMap.get("listOfRoleItems");
		assertEquals(2, roleListItems.size());
		
		@SuppressWarnings("unchecked")
		List<PermissionListItem> permissionListItems = (List<PermissionListItem>) modelMap.get("listOfPermissionItems");
		assertEquals(2, permissionListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		RolePermission rolePermission = rolePermissionFactoryForTest.newRolePermission();
		Integer rolePermissionId = rolePermission.getRolePermissionId();
		
		// When
		String viewName = rolePermissionController.delete(redirectAttributes, rolePermissionId);
		
		// Then
		verify(rolePermissionService).delete(rolePermissionId);
		assertEquals("redirect:/rolePermission", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		RolePermission rolePermission = rolePermissionFactoryForTest.newRolePermission();
		Integer rolePermissionId = rolePermission.getRolePermissionId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(rolePermissionService).delete(rolePermissionId);
		
		// When
		String viewName = rolePermissionController.delete(redirectAttributes, rolePermissionId);
		
		// Then
		verify(rolePermissionService).delete(rolePermissionId);
		assertEquals("redirect:/rolePermission", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "rolePermission.error.delete", exception);
	}
	
	
}
