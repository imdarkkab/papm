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
import com.nichesoft.bean.User;
import com.nichesoft.bean.Employee;
import com.nichesoft.test.UserFactoryForTest;
import com.nichesoft.test.EmployeeFactoryForTest;

//--- Services 
import com.nichesoft.business.service.UserService;
import com.nichesoft.business.service.EmployeeService;

//--- List Items 
import com.nichesoft.web.listitem.EmployeeListItem;

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
public class UserControllerTest {
	
	@InjectMocks
	private UserController userController;
	@Mock
	private UserService userService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private EmployeeService employeeService; // Injected by Spring

	private UserFactoryForTest userFactoryForTest = new UserFactoryForTest();
	private EmployeeFactoryForTest employeeFactoryForTest = new EmployeeFactoryForTest();

	List<Employee> employees = new ArrayList<Employee>();

	private void givenPopulateModel() {
		Employee employee1 = employeeFactoryForTest.newEmployee();
		Employee employee2 = employeeFactoryForTest.newEmployee();
		List<Employee> employees = new ArrayList<Employee>();
		employees.add(employee1);
		employees.add(employee2);
		when(employeeService.findAll()).thenReturn(employees);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<User> list = new ArrayList<User>();
		when(userService.findAll()).thenReturn(list);
		
		// When
		String viewName = userController.list(model);
		
		// Then
		assertEquals("user/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = userController.formForCreate(model);
		
		// Then
		assertEquals("user/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((User)modelMap.get("user")).getUserId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/user/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<EmployeeListItem> employeeListItems = (List<EmployeeListItem>) modelMap.get("listOfEmployeeItems");
		assertEquals(2, employeeListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		User user = userFactoryForTest.newUser();
		Integer userId = user.getUserId();
		when(userService.findById(userId)).thenReturn(user);
		
		// When
		String viewName = userController.formForUpdate(model, userId);
		
		// Then
		assertEquals("user/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(user, (User) modelMap.get("user"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/user/update", modelMap.get("saveAction"));
		
		List<EmployeeListItem> employeeListItems = (List<EmployeeListItem>) modelMap.get("listOfEmployeeItems");
		assertEquals(2, employeeListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		User user = userFactoryForTest.newUser();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		User userCreated = new User();
		when(userService.create(user)).thenReturn(userCreated); 
		
		// When
		String viewName = userController.create(user, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/user/form/"+user.getUserId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(userCreated, (User) modelMap.get("user"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		User user = userFactoryForTest.newUser();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = userController.create(user, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("user/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(user, (User) modelMap.get("user"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/user/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<EmployeeListItem> employeeListItems = (List<EmployeeListItem>) modelMap.get("listOfEmployeeItems");
		assertEquals(2, employeeListItems.size());
		
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

		User user = userFactoryForTest.newUser();
		
		Exception exception = new RuntimeException("test exception");
		when(userService.create(user)).thenThrow(exception);
		
		// When
		String viewName = userController.create(user, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("user/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(user, (User) modelMap.get("user"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/user/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "user.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<EmployeeListItem> employeeListItems = (List<EmployeeListItem>) modelMap.get("listOfEmployeeItems");
		assertEquals(2, employeeListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		User user = userFactoryForTest.newUser();
		Integer userId = user.getUserId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		User userSaved = new User();
		userSaved.setUserId(userId);
		when(userService.update(user)).thenReturn(userSaved); 
		
		// When
		String viewName = userController.update(user, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/user/form/"+user.getUserId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(userSaved, (User) modelMap.get("user"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		User user = userFactoryForTest.newUser();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = userController.update(user, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("user/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(user, (User) modelMap.get("user"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/user/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<EmployeeListItem> employeeListItems = (List<EmployeeListItem>) modelMap.get("listOfEmployeeItems");
		assertEquals(2, employeeListItems.size());
		
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

		User user = userFactoryForTest.newUser();
		
		Exception exception = new RuntimeException("test exception");
		when(userService.update(user)).thenThrow(exception);
		
		// When
		String viewName = userController.update(user, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("user/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(user, (User) modelMap.get("user"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/user/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "user.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<EmployeeListItem> employeeListItems = (List<EmployeeListItem>) modelMap.get("listOfEmployeeItems");
		assertEquals(2, employeeListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		User user = userFactoryForTest.newUser();
		Integer userId = user.getUserId();
		
		// When
		String viewName = userController.delete(redirectAttributes, userId);
		
		// Then
		verify(userService).delete(userId);
		assertEquals("redirect:/user", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		User user = userFactoryForTest.newUser();
		Integer userId = user.getUserId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(userService).delete(userId);
		
		// When
		String viewName = userController.delete(redirectAttributes, userId);
		
		// Then
		verify(userService).delete(userId);
		assertEquals("redirect:/user", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "user.error.delete", exception);
	}
	
	
}
