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
import com.nichesoft.bean.Messages;
import com.nichesoft.bean.Language;
import com.nichesoft.test.MessagesFactoryForTest;
import com.nichesoft.test.LanguageFactoryForTest;

//--- Services 
import com.nichesoft.business.service.MessagesService;
import com.nichesoft.business.service.LanguageService;

//--- List Items 
import com.nichesoft.web.listitem.LanguageListItem;

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
public class MessagesControllerTest {
	
	@InjectMocks
	private MessagesController messagesController;
	@Mock
	private MessagesService messagesService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private LanguageService languageService; // Injected by Spring

	private MessagesFactoryForTest messagesFactoryForTest = new MessagesFactoryForTest();
	private LanguageFactoryForTest languageFactoryForTest = new LanguageFactoryForTest();

	List<Language> languages = new ArrayList<Language>();

	private void givenPopulateModel() {
		Language language1 = languageFactoryForTest.newLanguage();
		Language language2 = languageFactoryForTest.newLanguage();
		List<Language> languages = new ArrayList<Language>();
		languages.add(language1);
		languages.add(language2);
		when(languageService.findAll()).thenReturn(languages);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Messages> list = new ArrayList<Messages>();
		when(messagesService.findAll()).thenReturn(list);
		
		// When
		String viewName = messagesController.list(model);
		
		// Then
		assertEquals("messages/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = messagesController.formForCreate(model);
		
		// Then
		assertEquals("messages/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Messages)modelMap.get("messages")).getMessageId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/messages/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<LanguageListItem> languageListItems = (List<LanguageListItem>) modelMap.get("listOfLanguageItems");
		assertEquals(2, languageListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Messages messages = messagesFactoryForTest.newMessages();
		Integer messageId = messages.getMessageId();
		when(messagesService.findById(messageId)).thenReturn(messages);
		
		// When
		String viewName = messagesController.formForUpdate(model, messageId);
		
		// Then
		assertEquals("messages/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(messages, (Messages) modelMap.get("messages"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/messages/update", modelMap.get("saveAction"));
		
		List<LanguageListItem> languageListItems = (List<LanguageListItem>) modelMap.get("listOfLanguageItems");
		assertEquals(2, languageListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Messages messages = messagesFactoryForTest.newMessages();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Messages messagesCreated = new Messages();
		when(messagesService.create(messages)).thenReturn(messagesCreated); 
		
		// When
		String viewName = messagesController.create(messages, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/messages/form/"+messages.getMessageId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(messagesCreated, (Messages) modelMap.get("messages"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Messages messages = messagesFactoryForTest.newMessages();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = messagesController.create(messages, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("messages/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(messages, (Messages) modelMap.get("messages"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/messages/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<LanguageListItem> languageListItems = (List<LanguageListItem>) modelMap.get("listOfLanguageItems");
		assertEquals(2, languageListItems.size());
		
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

		Messages messages = messagesFactoryForTest.newMessages();
		
		Exception exception = new RuntimeException("test exception");
		when(messagesService.create(messages)).thenThrow(exception);
		
		// When
		String viewName = messagesController.create(messages, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("messages/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(messages, (Messages) modelMap.get("messages"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/messages/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "messages.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<LanguageListItem> languageListItems = (List<LanguageListItem>) modelMap.get("listOfLanguageItems");
		assertEquals(2, languageListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Messages messages = messagesFactoryForTest.newMessages();
		Integer messageId = messages.getMessageId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Messages messagesSaved = new Messages();
		messagesSaved.setMessageId(messageId);
		when(messagesService.update(messages)).thenReturn(messagesSaved); 
		
		// When
		String viewName = messagesController.update(messages, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/messages/form/"+messages.getMessageId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(messagesSaved, (Messages) modelMap.get("messages"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Messages messages = messagesFactoryForTest.newMessages();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = messagesController.update(messages, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("messages/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(messages, (Messages) modelMap.get("messages"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/messages/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<LanguageListItem> languageListItems = (List<LanguageListItem>) modelMap.get("listOfLanguageItems");
		assertEquals(2, languageListItems.size());
		
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

		Messages messages = messagesFactoryForTest.newMessages();
		
		Exception exception = new RuntimeException("test exception");
		when(messagesService.update(messages)).thenThrow(exception);
		
		// When
		String viewName = messagesController.update(messages, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("messages/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(messages, (Messages) modelMap.get("messages"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/messages/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "messages.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<LanguageListItem> languageListItems = (List<LanguageListItem>) modelMap.get("listOfLanguageItems");
		assertEquals(2, languageListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Messages messages = messagesFactoryForTest.newMessages();
		Integer messageId = messages.getMessageId();
		
		// When
		String viewName = messagesController.delete(redirectAttributes, messageId);
		
		// Then
		verify(messagesService).delete(messageId);
		assertEquals("redirect:/messages", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Messages messages = messagesFactoryForTest.newMessages();
		Integer messageId = messages.getMessageId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(messagesService).delete(messageId);
		
		// When
		String viewName = messagesController.delete(redirectAttributes, messageId);
		
		// Then
		verify(messagesService).delete(messageId);
		assertEquals("redirect:/messages", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "messages.error.delete", exception);
	}
	
	
}
