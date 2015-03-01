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
import com.nichesoft.bean.Permission;
import com.nichesoft.bean.MenuSub;
import com.nichesoft.test.PermissionFactoryForTest;
import com.nichesoft.test.MenuSubFactoryForTest;

//--- Services 
import com.nichesoft.business.service.PermissionService;
import com.nichesoft.business.service.MenuSubService;

//--- List Items 
import com.nichesoft.web.listitem.MenuSubListItem;

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
public class PermissionControllerTest {
	
	@InjectMocks
	private PermissionController permissionController;
	@Mock
	private PermissionService permissionService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private MenuSubService menuSubService; // Injected by Spring

	private PermissionFactoryForTest permissionFactoryForTest = new PermissionFactoryForTest();
	private MenuSubFactoryForTest menuSubFactoryForTest = new MenuSubFactoryForTest();

	List<MenuSub> menuSubs = new ArrayList<MenuSub>();

	private void givenPopulateModel() {
		MenuSub menuSub1 = menuSubFactoryForTest.newMenuSub();
		MenuSub menuSub2 = menuSubFactoryForTest.newMenuSub();
		List<MenuSub> menuSubs = new ArrayList<MenuSub>();
		menuSubs.add(menuSub1);
		menuSubs.add(menuSub2);
		when(menuSubService.findAll()).thenReturn(menuSubs);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Permission> list = new ArrayList<Permission>();
		when(permissionService.findAll()).thenReturn(list);
		
		// When
		String viewName = permissionController.list(model);
		
		// Then
		assertEquals("permission/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = permissionController.formForCreate(model);
		
		// Then
		assertEquals("permission/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Permission)modelMap.get("permission")).getPermissionId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/permission/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<MenuSubListItem> menuSubListItems = (List<MenuSubListItem>) modelMap.get("listOfMenuSubItems");
		assertEquals(2, menuSubListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Permission permission = permissionFactoryForTest.newPermission();
		Integer permissionId = permission.getPermissionId();
		when(permissionService.findById(permissionId)).thenReturn(permission);
		
		// When
		String viewName = permissionController.formForUpdate(model, permissionId);
		
		// Then
		assertEquals("permission/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(permission, (Permission) modelMap.get("permission"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/permission/update", modelMap.get("saveAction"));
		
		List<MenuSubListItem> menuSubListItems = (List<MenuSubListItem>) modelMap.get("listOfMenuSubItems");
		assertEquals(2, menuSubListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Permission permission = permissionFactoryForTest.newPermission();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Permission permissionCreated = new Permission();
		when(permissionService.create(permission)).thenReturn(permissionCreated); 
		
		// When
		String viewName = permissionController.create(permission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/permission/form/"+permission.getPermissionId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(permissionCreated, (Permission) modelMap.get("permission"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Permission permission = permissionFactoryForTest.newPermission();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = permissionController.create(permission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("permission/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(permission, (Permission) modelMap.get("permission"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/permission/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<MenuSubListItem> menuSubListItems = (List<MenuSubListItem>) modelMap.get("listOfMenuSubItems");
		assertEquals(2, menuSubListItems.size());
		
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

		Permission permission = permissionFactoryForTest.newPermission();
		
		Exception exception = new RuntimeException("test exception");
		when(permissionService.create(permission)).thenThrow(exception);
		
		// When
		String viewName = permissionController.create(permission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("permission/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(permission, (Permission) modelMap.get("permission"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/permission/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "permission.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<MenuSubListItem> menuSubListItems = (List<MenuSubListItem>) modelMap.get("listOfMenuSubItems");
		assertEquals(2, menuSubListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Permission permission = permissionFactoryForTest.newPermission();
		Integer permissionId = permission.getPermissionId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Permission permissionSaved = new Permission();
		permissionSaved.setPermissionId(permissionId);
		when(permissionService.update(permission)).thenReturn(permissionSaved); 
		
		// When
		String viewName = permissionController.update(permission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/permission/form/"+permission.getPermissionId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(permissionSaved, (Permission) modelMap.get("permission"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Permission permission = permissionFactoryForTest.newPermission();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = permissionController.update(permission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("permission/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(permission, (Permission) modelMap.get("permission"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/permission/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<MenuSubListItem> menuSubListItems = (List<MenuSubListItem>) modelMap.get("listOfMenuSubItems");
		assertEquals(2, menuSubListItems.size());
		
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

		Permission permission = permissionFactoryForTest.newPermission();
		
		Exception exception = new RuntimeException("test exception");
		when(permissionService.update(permission)).thenThrow(exception);
		
		// When
		String viewName = permissionController.update(permission, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("permission/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(permission, (Permission) modelMap.get("permission"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/permission/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "permission.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<MenuSubListItem> menuSubListItems = (List<MenuSubListItem>) modelMap.get("listOfMenuSubItems");
		assertEquals(2, menuSubListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Permission permission = permissionFactoryForTest.newPermission();
		Integer permissionId = permission.getPermissionId();
		
		// When
		String viewName = permissionController.delete(redirectAttributes, permissionId);
		
		// Then
		verify(permissionService).delete(permissionId);
		assertEquals("redirect:/permission", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Permission permission = permissionFactoryForTest.newPermission();
		Integer permissionId = permission.getPermissionId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(permissionService).delete(permissionId);
		
		// When
		String viewName = permissionController.delete(redirectAttributes, permissionId);
		
		// Then
		verify(permissionService).delete(permissionId);
		assertEquals("redirect:/permission", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "permission.error.delete", exception);
	}
	
	
}
