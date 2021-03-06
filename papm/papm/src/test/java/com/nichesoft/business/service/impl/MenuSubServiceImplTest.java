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

import com.nichesoft.bean.MenuSub;
import com.nichesoft.bean.jpa.MenuSubEntity;
import java.util.List;
import com.nichesoft.business.service.mapping.MenuSubServiceMapper;
import com.nichesoft.data.repository.jpa.MenuSubJpaRepository;
import com.nichesoft.test.MenuSubFactoryForTest;
import com.nichesoft.test.MenuSubEntityFactoryForTest;
import com.nichesoft.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of MenuSubService
 */
@RunWith(MockitoJUnitRunner.class)
public class MenuSubServiceImplTest {

	@InjectMocks
	private MenuSubServiceImpl menuSubService;
	@Mock
	private MenuSubJpaRepository menuSubJpaRepository;
	@Mock
	private MenuSubServiceMapper menuSubServiceMapper;
	
	private MenuSubFactoryForTest menuSubFactoryForTest = new MenuSubFactoryForTest();

	private MenuSubEntityFactoryForTest menuSubEntityFactoryForTest = new MenuSubEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer menuSubIs = mockValues.nextInteger();
		
		MenuSubEntity menuSubEntity = menuSubJpaRepository.findOne(menuSubIs);
		
		MenuSub menuSub = menuSubFactoryForTest.newMenuSub();
		when(menuSubServiceMapper.mapMenuSubEntityToMenuSub(menuSubEntity)).thenReturn(menuSub);

		// When
		MenuSub menuSubFound = menuSubService.findById(menuSubIs);

		// Then
		assertEquals(menuSub.getMenuSubIs(),menuSubFound.getMenuSubIs());
	}

	@Test
	public void findAll() {
		// Given
		List<MenuSubEntity> menuSubEntitys = new ArrayList<MenuSubEntity>();
		MenuSubEntity menuSubEntity1 = menuSubEntityFactoryForTest.newMenuSubEntity();
		menuSubEntitys.add(menuSubEntity1);
		MenuSubEntity menuSubEntity2 = menuSubEntityFactoryForTest.newMenuSubEntity();
		menuSubEntitys.add(menuSubEntity2);
		when(menuSubJpaRepository.findAll()).thenReturn(menuSubEntitys);
		
		MenuSub menuSub1 = menuSubFactoryForTest.newMenuSub();
		when(menuSubServiceMapper.mapMenuSubEntityToMenuSub(menuSubEntity1)).thenReturn(menuSub1);
		MenuSub menuSub2 = menuSubFactoryForTest.newMenuSub();
		when(menuSubServiceMapper.mapMenuSubEntityToMenuSub(menuSubEntity2)).thenReturn(menuSub2);

		// When
		List<MenuSub> menuSubsFounds = menuSubService.findAll();

		// Then
		assertTrue(menuSub1 == menuSubsFounds.get(0));
		assertTrue(menuSub2 == menuSubsFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		MenuSub menuSub = menuSubFactoryForTest.newMenuSub();

		MenuSubEntity menuSubEntity = menuSubEntityFactoryForTest.newMenuSubEntity();
		when(menuSubJpaRepository.findOne(menuSub.getMenuSubIs())).thenReturn(null);
		
		menuSubEntity = new MenuSubEntity();
		menuSubServiceMapper.mapMenuSubToMenuSubEntity(menuSub, menuSubEntity);
		MenuSubEntity menuSubEntitySaved = menuSubJpaRepository.save(menuSubEntity);
		
		MenuSub menuSubSaved = menuSubFactoryForTest.newMenuSub();
		when(menuSubServiceMapper.mapMenuSubEntityToMenuSub(menuSubEntitySaved)).thenReturn(menuSubSaved);

		// When
		MenuSub menuSubResult = menuSubService.create(menuSub);

		// Then
		assertTrue(menuSubResult == menuSubSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		MenuSub menuSub = menuSubFactoryForTest.newMenuSub();

		MenuSubEntity menuSubEntity = menuSubEntityFactoryForTest.newMenuSubEntity();
		when(menuSubJpaRepository.findOne(menuSub.getMenuSubIs())).thenReturn(menuSubEntity);

		// When
		Exception exception = null;
		try {
			menuSubService.create(menuSub);
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
		MenuSub menuSub = menuSubFactoryForTest.newMenuSub();

		MenuSubEntity menuSubEntity = menuSubEntityFactoryForTest.newMenuSubEntity();
		when(menuSubJpaRepository.findOne(menuSub.getMenuSubIs())).thenReturn(menuSubEntity);
		
		MenuSubEntity menuSubEntitySaved = menuSubEntityFactoryForTest.newMenuSubEntity();
		when(menuSubJpaRepository.save(menuSubEntity)).thenReturn(menuSubEntitySaved);
		
		MenuSub menuSubSaved = menuSubFactoryForTest.newMenuSub();
		when(menuSubServiceMapper.mapMenuSubEntityToMenuSub(menuSubEntitySaved)).thenReturn(menuSubSaved);

		// When
		MenuSub menuSubResult = menuSubService.update(menuSub);

		// Then
		verify(menuSubServiceMapper).mapMenuSubToMenuSubEntity(menuSub, menuSubEntity);
		assertTrue(menuSubResult == menuSubSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer menuSubIs = mockValues.nextInteger();

		// When
		menuSubService.delete(menuSubIs);

		// Then
		verify(menuSubJpaRepository).delete(menuSubIs);
		
	}

}
