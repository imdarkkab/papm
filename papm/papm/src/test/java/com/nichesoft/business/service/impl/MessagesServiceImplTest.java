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

import com.nichesoft.bean.Messages;
import com.nichesoft.bean.jpa.MessagesEntity;
import java.util.List;
import com.nichesoft.business.service.mapping.MessagesServiceMapper;
import com.nichesoft.data.repository.jpa.MessagesJpaRepository;
import com.nichesoft.test.MessagesFactoryForTest;
import com.nichesoft.test.MessagesEntityFactoryForTest;
import com.nichesoft.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of MessagesService
 */
@RunWith(MockitoJUnitRunner.class)
public class MessagesServiceImplTest {

	@InjectMocks
	private MessagesServiceImpl messagesService;
	@Mock
	private MessagesJpaRepository messagesJpaRepository;
	@Mock
	private MessagesServiceMapper messagesServiceMapper;
	
	private MessagesFactoryForTest messagesFactoryForTest = new MessagesFactoryForTest();

	private MessagesEntityFactoryForTest messagesEntityFactoryForTest = new MessagesEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer messageId = mockValues.nextInteger();
		
		MessagesEntity messagesEntity = messagesJpaRepository.findOne(messageId);
		
		Messages messages = messagesFactoryForTest.newMessages();
		when(messagesServiceMapper.mapMessagesEntityToMessages(messagesEntity)).thenReturn(messages);

		// When
		Messages messagesFound = messagesService.findById(messageId);

		// Then
		assertEquals(messages.getMessageId(),messagesFound.getMessageId());
	}

	@Test
	public void findAll() {
		// Given
		List<MessagesEntity> messagesEntitys = new ArrayList<MessagesEntity>();
		MessagesEntity messagesEntity1 = messagesEntityFactoryForTest.newMessagesEntity();
		messagesEntitys.add(messagesEntity1);
		MessagesEntity messagesEntity2 = messagesEntityFactoryForTest.newMessagesEntity();
		messagesEntitys.add(messagesEntity2);
		when(messagesJpaRepository.findAll()).thenReturn(messagesEntitys);
		
		Messages messages1 = messagesFactoryForTest.newMessages();
		when(messagesServiceMapper.mapMessagesEntityToMessages(messagesEntity1)).thenReturn(messages1);
		Messages messages2 = messagesFactoryForTest.newMessages();
		when(messagesServiceMapper.mapMessagesEntityToMessages(messagesEntity2)).thenReturn(messages2);

		// When
		List<Messages> messagessFounds = messagesService.findAll();

		// Then
		assertTrue(messages1 == messagessFounds.get(0));
		assertTrue(messages2 == messagessFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Messages messages = messagesFactoryForTest.newMessages();

		MessagesEntity messagesEntity = messagesEntityFactoryForTest.newMessagesEntity();
		when(messagesJpaRepository.findOne(messages.getMessageId())).thenReturn(null);
		
		messagesEntity = new MessagesEntity();
		messagesServiceMapper.mapMessagesToMessagesEntity(messages, messagesEntity);
		MessagesEntity messagesEntitySaved = messagesJpaRepository.save(messagesEntity);
		
		Messages messagesSaved = messagesFactoryForTest.newMessages();
		when(messagesServiceMapper.mapMessagesEntityToMessages(messagesEntitySaved)).thenReturn(messagesSaved);

		// When
		Messages messagesResult = messagesService.create(messages);

		// Then
		assertTrue(messagesResult == messagesSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Messages messages = messagesFactoryForTest.newMessages();

		MessagesEntity messagesEntity = messagesEntityFactoryForTest.newMessagesEntity();
		when(messagesJpaRepository.findOne(messages.getMessageId())).thenReturn(messagesEntity);

		// When
		Exception exception = null;
		try {
			messagesService.create(messages);
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
		Messages messages = messagesFactoryForTest.newMessages();

		MessagesEntity messagesEntity = messagesEntityFactoryForTest.newMessagesEntity();
		when(messagesJpaRepository.findOne(messages.getMessageId())).thenReturn(messagesEntity);
		
		MessagesEntity messagesEntitySaved = messagesEntityFactoryForTest.newMessagesEntity();
		when(messagesJpaRepository.save(messagesEntity)).thenReturn(messagesEntitySaved);
		
		Messages messagesSaved = messagesFactoryForTest.newMessages();
		when(messagesServiceMapper.mapMessagesEntityToMessages(messagesEntitySaved)).thenReturn(messagesSaved);

		// When
		Messages messagesResult = messagesService.update(messages);

		// Then
		verify(messagesServiceMapper).mapMessagesToMessagesEntity(messages, messagesEntity);
		assertTrue(messagesResult == messagesSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer messageId = mockValues.nextInteger();

		// When
		messagesService.delete(messageId);

		// Then
		verify(messagesJpaRepository).delete(messageId);
		
	}

}
