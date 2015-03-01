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
import com.nichesoft.bean.Employee;
import com.nichesoft.test.EmployeeFactoryForTest;

//--- Services 
import com.nichesoft.business.service.EmployeeService;


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
public class EmployeeControllerTest {
	
	@InjectMocks
	private EmployeeController employeeController;
	@Mock
	private EmployeeService employeeService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private EmployeeFactoryForTest employeeFactoryForTest = new EmployeeFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Employee> list = new ArrayList<Employee>();
		when(employeeService.findAll()).thenReturn(list);
		
		// When
		String viewName = employeeController.list(model);
		
		// Then
		assertEquals("employee/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = employeeController.formForCreate(model);
		
		// Then
		assertEquals("employee/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Employee)modelMap.get("employee")).getEmployeeId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/employee/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Employee employee = employeeFactoryForTest.newEmployee();
		Integer employeeId = employee.getEmployeeId();
		when(employeeService.findById(employeeId)).thenReturn(employee);
		
		// When
		String viewName = employeeController.formForUpdate(model, employeeId);
		
		// Then
		assertEquals("employee/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(employee, (Employee) modelMap.get("employee"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/employee/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Employee employee = employeeFactoryForTest.newEmployee();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Employee employeeCreated = new Employee();
		when(employeeService.create(employee)).thenReturn(employeeCreated); 
		
		// When
		String viewName = employeeController.create(employee, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/employee/form/"+employee.getEmployeeId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(employeeCreated, (Employee) modelMap.get("employee"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Employee employee = employeeFactoryForTest.newEmployee();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = employeeController.create(employee, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("employee/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(employee, (Employee) modelMap.get("employee"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/employee/create", modelMap.get("saveAction"));
		
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

		Employee employee = employeeFactoryForTest.newEmployee();
		
		Exception exception = new RuntimeException("test exception");
		when(employeeService.create(employee)).thenThrow(exception);
		
		// When
		String viewName = employeeController.create(employee, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("employee/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(employee, (Employee) modelMap.get("employee"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/employee/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "employee.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Employee employee = employeeFactoryForTest.newEmployee();
		Integer employeeId = employee.getEmployeeId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Employee employeeSaved = new Employee();
		employeeSaved.setEmployeeId(employeeId);
		when(employeeService.update(employee)).thenReturn(employeeSaved); 
		
		// When
		String viewName = employeeController.update(employee, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/employee/form/"+employee.getEmployeeId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(employeeSaved, (Employee) modelMap.get("employee"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Employee employee = employeeFactoryForTest.newEmployee();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = employeeController.update(employee, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("employee/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(employee, (Employee) modelMap.get("employee"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/employee/update", modelMap.get("saveAction"));
		
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

		Employee employee = employeeFactoryForTest.newEmployee();
		
		Exception exception = new RuntimeException("test exception");
		when(employeeService.update(employee)).thenThrow(exception);
		
		// When
		String viewName = employeeController.update(employee, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("employee/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(employee, (Employee) modelMap.get("employee"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/employee/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "employee.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Employee employee = employeeFactoryForTest.newEmployee();
		Integer employeeId = employee.getEmployeeId();
		
		// When
		String viewName = employeeController.delete(redirectAttributes, employeeId);
		
		// Then
		verify(employeeService).delete(employeeId);
		assertEquals("redirect:/employee", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Employee employee = employeeFactoryForTest.newEmployee();
		Integer employeeId = employee.getEmployeeId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(employeeService).delete(employeeId);
		
		// When
		String viewName = employeeController.delete(redirectAttributes, employeeId);
		
		// Then
		verify(employeeService).delete(employeeId);
		assertEquals("redirect:/employee", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "employee.error.delete", exception);
	}
	
	
}
