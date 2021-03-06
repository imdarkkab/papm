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

import com.nichesoft.bean.Employee;
import com.nichesoft.bean.jpa.EmployeeEntity;
import java.util.Date;
import java.util.List;
import com.nichesoft.business.service.mapping.EmployeeServiceMapper;
import com.nichesoft.data.repository.jpa.EmployeeJpaRepository;
import com.nichesoft.test.EmployeeFactoryForTest;
import com.nichesoft.test.EmployeeEntityFactoryForTest;
import com.nichesoft.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of EmployeeService
 */
@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

	@InjectMocks
	private EmployeeServiceImpl employeeService;
	@Mock
	private EmployeeJpaRepository employeeJpaRepository;
	@Mock
	private EmployeeServiceMapper employeeServiceMapper;
	
	private EmployeeFactoryForTest employeeFactoryForTest = new EmployeeFactoryForTest();

	private EmployeeEntityFactoryForTest employeeEntityFactoryForTest = new EmployeeEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer employeeId = mockValues.nextInteger();
		
		EmployeeEntity employeeEntity = employeeJpaRepository.findOne(employeeId);
		
		Employee employee = employeeFactoryForTest.newEmployee();
		when(employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity)).thenReturn(employee);

		// When
		Employee employeeFound = employeeService.findById(employeeId);

		// Then
		assertEquals(employee.getEmployeeId(),employeeFound.getEmployeeId());
	}

	@Test
	public void findAll() {
		// Given
		List<EmployeeEntity> employeeEntitys = new ArrayList<EmployeeEntity>();
		EmployeeEntity employeeEntity1 = employeeEntityFactoryForTest.newEmployeeEntity();
		employeeEntitys.add(employeeEntity1);
		EmployeeEntity employeeEntity2 = employeeEntityFactoryForTest.newEmployeeEntity();
		employeeEntitys.add(employeeEntity2);
		when(employeeJpaRepository.findAll()).thenReturn(employeeEntitys);
		
		Employee employee1 = employeeFactoryForTest.newEmployee();
		when(employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity1)).thenReturn(employee1);
		Employee employee2 = employeeFactoryForTest.newEmployee();
		when(employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity2)).thenReturn(employee2);

		// When
		List<Employee> employeesFounds = employeeService.findAll();

		// Then
		assertTrue(employee1 == employeesFounds.get(0));
		assertTrue(employee2 == employeesFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Employee employee = employeeFactoryForTest.newEmployee();

		EmployeeEntity employeeEntity = employeeEntityFactoryForTest.newEmployeeEntity();
		when(employeeJpaRepository.findOne(employee.getEmployeeId())).thenReturn(null);
		
		employeeEntity = new EmployeeEntity();
		employeeServiceMapper.mapEmployeeToEmployeeEntity(employee, employeeEntity);
		EmployeeEntity employeeEntitySaved = employeeJpaRepository.save(employeeEntity);
		
		Employee employeeSaved = employeeFactoryForTest.newEmployee();
		when(employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntitySaved)).thenReturn(employeeSaved);

		// When
		Employee employeeResult = employeeService.create(employee);

		// Then
		assertTrue(employeeResult == employeeSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Employee employee = employeeFactoryForTest.newEmployee();

		EmployeeEntity employeeEntity = employeeEntityFactoryForTest.newEmployeeEntity();
		when(employeeJpaRepository.findOne(employee.getEmployeeId())).thenReturn(employeeEntity);

		// When
		Exception exception = null;
		try {
			employeeService.create(employee);
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
		Employee employee = employeeFactoryForTest.newEmployee();

		EmployeeEntity employeeEntity = employeeEntityFactoryForTest.newEmployeeEntity();
		when(employeeJpaRepository.findOne(employee.getEmployeeId())).thenReturn(employeeEntity);
		
		EmployeeEntity employeeEntitySaved = employeeEntityFactoryForTest.newEmployeeEntity();
		when(employeeJpaRepository.save(employeeEntity)).thenReturn(employeeEntitySaved);
		
		Employee employeeSaved = employeeFactoryForTest.newEmployee();
		when(employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntitySaved)).thenReturn(employeeSaved);

		// When
		Employee employeeResult = employeeService.update(employee);

		// Then
		verify(employeeServiceMapper).mapEmployeeToEmployeeEntity(employee, employeeEntity);
		assertTrue(employeeResult == employeeSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer employeeId = mockValues.nextInteger();

		// When
		employeeService.delete(employeeId);

		// Then
		verify(employeeJpaRepository).delete(employeeId);
		
	}

}
