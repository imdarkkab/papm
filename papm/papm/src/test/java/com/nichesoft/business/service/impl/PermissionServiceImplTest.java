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

import com.nichesoft.bean.Permission;
import com.nichesoft.bean.jpa.PermissionEntity;
import java.util.List;
import com.nichesoft.business.service.mapping.PermissionServiceMapper;
import com.nichesoft.data.repository.jpa.PermissionJpaRepository;
import com.nichesoft.test.PermissionFactoryForTest;
import com.nichesoft.test.PermissionEntityFactoryForTest;
import com.nichesoft.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of PermissionService
 */
@RunWith(MockitoJUnitRunner.class)
public class PermissionServiceImplTest {

	@InjectMocks
	private PermissionServiceImpl permissionService;
	@Mock
	private PermissionJpaRepository permissionJpaRepository;
	@Mock
	private PermissionServiceMapper permissionServiceMapper;
	
	private PermissionFactoryForTest permissionFactoryForTest = new PermissionFactoryForTest();

	private PermissionEntityFactoryForTest permissionEntityFactoryForTest = new PermissionEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer permissionId = mockValues.nextInteger();
		
		PermissionEntity permissionEntity = permissionJpaRepository.findOne(permissionId);
		
		Permission permission = permissionFactoryForTest.newPermission();
		when(permissionServiceMapper.mapPermissionEntityToPermission(permissionEntity)).thenReturn(permission);

		// When
		Permission permissionFound = permissionService.findById(permissionId);

		// Then
		assertEquals(permission.getPermissionId(),permissionFound.getPermissionId());
	}

	@Test
	public void findAll() {
		// Given
		List<PermissionEntity> permissionEntitys = new ArrayList<PermissionEntity>();
		PermissionEntity permissionEntity1 = permissionEntityFactoryForTest.newPermissionEntity();
		permissionEntitys.add(permissionEntity1);
		PermissionEntity permissionEntity2 = permissionEntityFactoryForTest.newPermissionEntity();
		permissionEntitys.add(permissionEntity2);
		when(permissionJpaRepository.findAll()).thenReturn(permissionEntitys);
		
		Permission permission1 = permissionFactoryForTest.newPermission();
		when(permissionServiceMapper.mapPermissionEntityToPermission(permissionEntity1)).thenReturn(permission1);
		Permission permission2 = permissionFactoryForTest.newPermission();
		when(permissionServiceMapper.mapPermissionEntityToPermission(permissionEntity2)).thenReturn(permission2);

		// When
		List<Permission> permissionsFounds = permissionService.findAll();

		// Then
		assertTrue(permission1 == permissionsFounds.get(0));
		assertTrue(permission2 == permissionsFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Permission permission = permissionFactoryForTest.newPermission();

		PermissionEntity permissionEntity = permissionEntityFactoryForTest.newPermissionEntity();
		when(permissionJpaRepository.findOne(permission.getPermissionId())).thenReturn(null);
		
		permissionEntity = new PermissionEntity();
		permissionServiceMapper.mapPermissionToPermissionEntity(permission, permissionEntity);
		PermissionEntity permissionEntitySaved = permissionJpaRepository.save(permissionEntity);
		
		Permission permissionSaved = permissionFactoryForTest.newPermission();
		when(permissionServiceMapper.mapPermissionEntityToPermission(permissionEntitySaved)).thenReturn(permissionSaved);

		// When
		Permission permissionResult = permissionService.create(permission);

		// Then
		assertTrue(permissionResult == permissionSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Permission permission = permissionFactoryForTest.newPermission();

		PermissionEntity permissionEntity = permissionEntityFactoryForTest.newPermissionEntity();
		when(permissionJpaRepository.findOne(permission.getPermissionId())).thenReturn(permissionEntity);

		// When
		Exception exception = null;
		try {
			permissionService.create(permission);
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
		Permission permission = permissionFactoryForTest.newPermission();

		PermissionEntity permissionEntity = permissionEntityFactoryForTest.newPermissionEntity();
		when(permissionJpaRepository.findOne(permission.getPermissionId())).thenReturn(permissionEntity);
		
		PermissionEntity permissionEntitySaved = permissionEntityFactoryForTest.newPermissionEntity();
		when(permissionJpaRepository.save(permissionEntity)).thenReturn(permissionEntitySaved);
		
		Permission permissionSaved = permissionFactoryForTest.newPermission();
		when(permissionServiceMapper.mapPermissionEntityToPermission(permissionEntitySaved)).thenReturn(permissionSaved);

		// When
		Permission permissionResult = permissionService.update(permission);

		// Then
		verify(permissionServiceMapper).mapPermissionToPermissionEntity(permission, permissionEntity);
		assertTrue(permissionResult == permissionSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer permissionId = mockValues.nextInteger();

		// When
		permissionService.delete(permissionId);

		// Then
		verify(permissionJpaRepository).delete(permissionId);
		
	}

}
