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
import com.nichesoft.bean.Role;
import com.nichesoft.test.RoleFactoryForTest;

//--- Services 
import com.nichesoft.business.service.RoleService;


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
public class RoleControllerTest {
	
	@InjectMocks
	private RoleController roleController;
	@Mock
	private RoleService roleService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private RoleFactoryForTest roleFactoryForTest = new RoleFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Role> list = new ArrayList<Role>();
		when(roleService.findAll()).thenReturn(list);
		
		// When
		String viewName = roleController.list(model);
		
		// Then
		assertEquals("role/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = roleController.formForCreate(model);
		
		// Then
		assertEquals("role/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Role)modelMap.get("role")).getRoleId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/role/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Role role = roleFactoryForTest.newRole();
		Integer roleId = role.getRoleId();
		when(roleService.findById(roleId)).thenReturn(role);
		
		// When
		String viewName = roleController.formForUpdate(model, roleId);
		
		// Then
		assertEquals("role/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(role, (Role) modelMap.get("role"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/role/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Role role = roleFactoryForTest.newRole();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Role roleCreated = new Role();
		when(roleService.create(role)).thenReturn(roleCreated); 
		
		// When
		String viewName = roleController.create(role, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/role/form/"+role.getRoleId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(roleCreated, (Role) modelMap.get("role"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Role role = roleFactoryForTest.newRole();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = roleController.create(role, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("role/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(role, (Role) modelMap.get("role"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/role/create", modelMap.get("saveAction"));
		
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

		Role role = roleFactoryForTest.newRole();
		
		Exception exception = new RuntimeException("test exception");
		when(roleService.create(role)).thenThrow(exception);
		
		// When
		String viewName = roleController.create(role, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("role/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(role, (Role) modelMap.get("role"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/role/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "role.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Role role = roleFactoryForTest.newRole();
		Integer roleId = role.getRoleId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Role roleSaved = new Role();
		roleSaved.setRoleId(roleId);
		when(roleService.update(role)).thenReturn(roleSaved); 
		
		// When
		String viewName = roleController.update(role, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/role/form/"+role.getRoleId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(roleSaved, (Role) modelMap.get("role"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Role role = roleFactoryForTest.newRole();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = roleController.update(role, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("role/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(role, (Role) modelMap.get("role"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/role/update", modelMap.get("saveAction"));
		
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

		Role role = roleFactoryForTest.newRole();
		
		Exception exception = new RuntimeException("test exception");
		when(roleService.update(role)).thenThrow(exception);
		
		// When
		String viewName = roleController.update(role, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("role/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(role, (Role) modelMap.get("role"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/role/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "role.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Role role = roleFactoryForTest.newRole();
		Integer roleId = role.getRoleId();
		
		// When
		String viewName = roleController.delete(redirectAttributes, roleId);
		
		// Then
		verify(roleService).delete(roleId);
		assertEquals("redirect:/role", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Role role = roleFactoryForTest.newRole();
		Integer roleId = role.getRoleId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(roleService).delete(roleId);
		
		// When
		String viewName = roleController.delete(redirectAttributes, roleId);
		
		// Then
		verify(roleService).delete(roleId);
		assertEquals("redirect:/role", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "role.error.delete", exception);
	}
	
	
}
