/*
 * Created on 27 �.�. 2558 ( Time 16:44:19 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.nichesoft.bean.Employee;
import com.nichesoft.bean.jpa.EmployeeEntity;
import java.util.Date;
import java.util.List;
import com.nichesoft.business.service.EmployeeService;
import com.nichesoft.business.service.mapping.EmployeeServiceMapper;
import com.nichesoft.data.repository.jpa.EmployeeJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of EmployeeService
 */
@Component
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Resource
	private EmployeeJpaRepository employeeJpaRepository;

	@Resource
	private EmployeeServiceMapper employeeServiceMapper;
	
	@Override
	public Employee findById(Integer employeeId) {
		EmployeeEntity employeeEntity = employeeJpaRepository.findOne(employeeId);
		return employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity);
	}

	@Override
	public List<Employee> findAll() {
		Iterable<EmployeeEntity> entities = employeeJpaRepository.findAll();
		List<Employee> beans = new ArrayList<Employee>();
		for(EmployeeEntity employeeEntity : entities) {
			beans.add(employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntity));
		}
		return beans;
	}

	@Override
	public Employee save(Employee employee) {
		return update(employee) ;
	}

	@Override
	public Employee create(Employee employee) {
		EmployeeEntity employeeEntity = employeeJpaRepository.findOne(employee.getEmployeeId());
		if( employeeEntity != null ) {
			throw new IllegalStateException("already.exists");
		}
		employeeEntity = new EmployeeEntity();
		employeeServiceMapper.mapEmployeeToEmployeeEntity(employee, employeeEntity);
		EmployeeEntity employeeEntitySaved = employeeJpaRepository.save(employeeEntity);
		return employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntitySaved);
	}

	@Override
	public Employee update(Employee employee) {
		EmployeeEntity employeeEntity = employeeJpaRepository.findOne(employee.getEmployeeId());
		employeeServiceMapper.mapEmployeeToEmployeeEntity(employee, employeeEntity);
		EmployeeEntity employeeEntitySaved = employeeJpaRepository.save(employeeEntity);
		return employeeServiceMapper.mapEmployeeEntityToEmployee(employeeEntitySaved);
	}

	@Override
	public void delete(Integer employeeId) {
		employeeJpaRepository.delete(employeeId);
	}

	public EmployeeJpaRepository getEmployeeJpaRepository() {
		return employeeJpaRepository;
	}

	public void setEmployeeJpaRepository(EmployeeJpaRepository employeeJpaRepository) {
		this.employeeJpaRepository = employeeJpaRepository;
	}

	public EmployeeServiceMapper getEmployeeServiceMapper() {
		return employeeServiceMapper;
	}

	public void setEmployeeServiceMapper(EmployeeServiceMapper employeeServiceMapper) {
		this.employeeServiceMapper = employeeServiceMapper;
	}

}
