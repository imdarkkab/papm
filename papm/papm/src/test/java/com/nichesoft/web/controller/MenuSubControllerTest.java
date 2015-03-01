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
import com.nichesoft.bean.MenuSub;
import com.nichesoft.bean.Messages;
import com.nichesoft.test.MenuSubFactoryForTest;
import com.nichesoft.test.MessagesFactoryForTest;

//--- Services 
import com.nichesoft.business.service.MenuSubService;
import com.nichesoft.business.service.MessagesService;

//--- List Items 
import com.nichesoft.web.listitem.MessagesListItem;

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
public class MenuSubControllerTest {
	
	@InjectMocks
	private MenuSubController menuSubController;
	@Mock
	private MenuSubService menuSubService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private MessagesService messagesService; // Injected by Spring

	private MenuSubFactoryForTest menuSubFactoryForTest = new MenuSubFactoryForTest();
	private MessagesFactoryForTest messagesFactoryForTest = new MessagesFactoryForTest();

	List<Messages> messagess = new ArrayList<Messages>();

	private void givenPopulateModel() {
		Messages messages1 = messagesFactoryForTest.newMessages();
		Messages messages2 = messagesFactoryForTest.newMessages();
		List<Messages> messagess = new ArrayList<Messages>();
		messagess.add(messages1);
		messagess.add(messages2);
		when(messagesService.findAll()).thenReturn(messagess);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<MenuSub> list = new ArrayList<MenuSub>();
		when(menuSubService.findAll()).thenReturn(list);
		
		// When
		String viewName = menuSubController.list(model);
		
		// Then
		assertEquals("menuSub/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = menuSubController.formForCreate(model);
		
		// Then
		assertEquals("menuSub/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((MenuSub)modelMap.get("menuSub")).getMenuSubIs());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/menuSub/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<MessagesListItem> messagesListItems = (List<MessagesListItem>) modelMap.get("listOfMessagesItems");
		assertEquals(2, messagesListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		MenuSub menuSub = menuSubFactoryForTest.newMenuSub();
		Integer menuSubIs = menuSub.getMenuSubIs();
		when(menuSubService.findById(menuSubIs)).thenReturn(menuSub);
		
		// When
		String viewName = menuSubController.formForUpdate(model, menuSubIs);
		
		// Then
		assertEquals("menuSub/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(menuSub, (MenuSub) modelMap.get("menuSub"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/menuSub/update", modelMap.get("saveAction"));
		
		List<MessagesListItem> messagesListItems = (List<MessagesListItem>) modelMap.get("listOfMessagesItems");
		assertEquals(2, messagesListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		MenuSub menuSub = menuSubFactoryForTest.newMenuSub();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		MenuSub menuSubCreated = new MenuSub();
		when(menuSubService.create(menuSub)).thenReturn(menuSubCreated); 
		
		// When
		String viewName = menuSubController.create(menuSub, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/menuSub/form/"+menuSub.getMenuSubIs(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(menuSubCreated, (MenuSub) modelMap.get("menuSub"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		MenuSub menuSub = menuSubFactoryForTest.newMenuSub();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = menuSubController.create(menuSub, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("menuSub/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(menuSub, (MenuSub) modelMap.get("menuSub"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/menuSub/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<MessagesListItem> messagesListItems = (List<MessagesListItem>) modelMap.get("listOfMessagesItems");
		assertEquals(2, messagesListItems.size());
		
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

		MenuSub menuSub = menuSubFactoryForTest.newMenuSub();
		
		Exception exception = new RuntimeException("test exception");
		when(menuSubService.create(menuSub)).thenThrow(exception);
		
		// When
		String viewName = menuSubController.create(menuSub, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("menuSub/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(menuSub, (MenuSub) modelMap.get("menuSub"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/menuSub/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "menuSub.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<MessagesListItem> messagesListItems = (List<MessagesListItem>) modelMap.get("listOfMessagesItems");
		assertEquals(2, messagesListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		MenuSub menuSub = menuSubFactoryForTest.newMenuSub();
		Integer menuSubIs = menuSub.getMenuSubIs();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		MenuSub menuSubSaved = new MenuSub();
		menuSubSaved.setMenuSubIs(menuSubIs);
		when(menuSubService.update(menuSub)).thenReturn(menuSubSaved); 
		
		// When
		String viewName = menuSubController.update(menuSub, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/menuSub/form/"+menuSub.getMenuSubIs(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(menuSubSaved, (MenuSub) modelMap.get("menuSub"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		MenuSub menuSub = menuSubFactoryForTest.newMenuSub();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = menuSubController.update(menuSub, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("menuSub/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(menuSub, (MenuSub) modelMap.get("menuSub"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/menuSub/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<MessagesListItem> messagesListItems = (List<MessagesListItem>) modelMap.get("listOfMessagesItems");
		assertEquals(2, messagesListItems.size());
		
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

		MenuSub menuSub = menuSubFactoryForTest.newMenuSub();
		
		Exception exception = new RuntimeException("test exception");
		when(menuSubService.update(menuSub)).thenThrow(exception);
		
		// When
		String viewName = menuSubController.update(menuSub, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("menuSub/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(menuSub, (MenuSub) modelMap.get("menuSub"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/menuSub/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "menuSub.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<MessagesListItem> messagesListItems = (List<MessagesListItem>) modelMap.get("listOfMessagesItems");
		assertEquals(2, messagesListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		MenuSub menuSub = menuSubFactoryForTest.newMenuSub();
		Integer menuSubIs = menuSub.getMenuSubIs();
		
		// When
		String viewName = menuSubController.delete(redirectAttributes, menuSubIs);
		
		// Then
		verify(menuSubService).delete(menuSubIs);
		assertEquals("redirect:/menuSub", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		MenuSub menuSub = menuSubFactoryForTest.newMenuSub();
		Integer menuSubIs = menuSub.getMenuSubIs();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(menuSubService).delete(menuSubIs);
		
		// When
		String viewName = menuSubController.delete(redirectAttributes, menuSubIs);
		
		// Then
		verify(menuSubService).delete(menuSubIs);
		assertEquals("redirect:/menuSub", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "menuSub.error.delete", exception);
	}
	
	
}
