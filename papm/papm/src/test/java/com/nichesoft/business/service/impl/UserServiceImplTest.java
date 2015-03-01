/*
 * Created on 28 �.�. 2558 ( Time 11:56:52 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.nichesoft.bean.User;
import com.nichesoft.bean.jpa.UserEntity;
import java.util.Date;
import java.util.List;
import com.nichesoft.business.service.mapping.UserServiceMapper;
import com.nichesoft.data.repository.jpa.UserJpaRepository;
import com.nichesoft.test.UserFactoryForTest;
import com.nichesoft.test.UserEntityFactoryForTest;
import com.nichesoft.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of UserService
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@InjectMocks
	private UserServiceImpl userService;
	@Mock
	private UserJpaRepository userJpaRepository;
	@Mock
	private UserServiceMapper userServiceMapper;
	
	private UserFactoryForTest userFactoryForTest = new UserFactoryForTest();

	private UserEntityFactoryForTest userEntityFactoryForTest = new UserEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer userId = mockValues.nextInteger();
		
		UserEntity userEntity = userJpaRepository.findOne(userId);
		
		User user = userFactoryForTest.newUser();
		when(userServiceMapper.mapUserEntityToUser(userEntity)).thenReturn(user);

		// When
		User userFound = userService.findById(userId);

		// Then
		assertEquals(user.getUserId(),userFound.getUserId());
	}

	@Test
	public void findAll() {
		// Given
		List<UserEntity> userEntitys = new ArrayList<UserEntity>();
		UserEntity userEntity1 = userEntityFactoryForTest.newUserEntity();
		userEntitys.add(userEntity1);
		UserEntity userEntity2 = userEntityFactoryForTest.newUserEntity();
		userEntitys.add(userEntity2);
		when(userJpaRepository.findAll()).thenReturn(userEntitys);
		
		User user1 = userFactoryForTest.newUser();
		when(userServiceMapper.mapUserEntityToUser(userEntity1)).thenReturn(user1);
		User user2 = userFactoryForTest.newUser();
		when(userServiceMapper.mapUserEntityToUser(userEntity2)).thenReturn(user2);

		// When
		List<User> usersFounds = userService.findAll();

		// Then
		assertTrue(user1 == usersFounds.get(0));
		assertTrue(user2 == usersFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		User user = userFactoryForTest.newUser();

		UserEntity userEntity = userEntityFactoryForTest.newUserEntity();
		when(userJpaRepository.findOne(user.getUserId())).thenReturn(null);
		
		userEntity = new UserEntity();
		userServiceMapper.mapUserToUserEntity(user, userEntity);
		UserEntity userEntitySaved = userJpaRepository.save(userEntity);
		
		User userSaved = userFactoryForTest.newUser();
		when(userServiceMapper.mapUserEntityToUser(userEntitySaved)).thenReturn(userSaved);

		// When
		User userResult = userService.create(user);

		// Then
		assertTrue(userResult == userSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		User user = userFactoryForTest.newUser();

		UserEntity userEntity = userEntityFactoryForTest.newUserEntity();
		when(userJpaRepository.findOne(user.getUserId())).thenReturn(userEntity);

		// When
		Exception exception = null;
		try {
			userService.create(user);
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
		User user = userFactoryForTest.newUser();

		UserEntity userEntity = userEntityFactoryForTest.newUserEntity();
		when(userJpaRepository.findOne(user.getUserId())).thenReturn(userEntity);
		
		UserEntity userEntitySaved = userEntityFactoryForTest.newUserEntity();
		when(userJpaRepository.save(userEntity)).thenReturn(userEntitySaved);
		
		User userSaved = userFactoryForTest.newUser();
		when(userServiceMapper.mapUserEntityToUser(userEntitySaved)).thenReturn(userSaved);

		// When
		User userResult = userService.update(user);

		// Then
		verify(userServiceMapper).mapUserToUserEntity(user, userEntity);
		assertTrue(userResult == userSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer userId = mockValues.nextInteger();

		// When
		userService.delete(userId);

		// Then
		verify(userJpaRepository).delete(userId);
		
	}

}