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
import com.nichesoft.bean.MenuMain;
import com.nichesoft.bean.Messages;
import com.nichesoft.test.MenuMainFactoryForTest;
import com.nichesoft.test.MessagesFactoryForTest;

//--- Services 
import com.nichesoft.business.service.MenuMainService;
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
public class MenuMainControllerTest {
	
	@InjectMocks
	private MenuMainController menuMainController;
	@Mock
	private MenuMainService menuMainService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private MessagesService messagesService; // Injected by Spring

	private MenuMainFactoryForTest menuMainFactoryForTest = new MenuMainFactoryForTest();
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
		
		List<MenuMain> list = new ArrayList<MenuMain>();
		when(menuMainService.findAll()).thenReturn(list);
		
		// When
		String viewName = menuMainController.list(model);
		
		// Then
		assertEquals("menuMain/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = menuMainController.formForCreate(model);
		
		// Then
		assertEquals("menuMain/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((MenuMain)modelMap.get("menuMain")).getMenuMainId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/menuMain/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<MessagesListItem> messagesListItems = (List<MessagesListItem>) modelMap.get("listOfMessagesItems");
		assertEquals(2, messagesListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		MenuMain menuMain = menuMainFactoryForTest.newMenuMain();
		Integer menuMainId = menuMain.getMenuMainId();
		when(menuMainService.findById(menuMainId)).thenReturn(menuMain);
		
		// When
		String viewName = menuMainController.formForUpdate(model, menuMainId);
		
		// Then
		assertEquals("menuMain/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(menuMain, (MenuMain) modelMap.get("menuMain"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/menuMain/update", modelMap.get("saveAction"));
		
		List<MessagesListItem> messagesListItems = (List<MessagesListItem>) modelMap.get("listOfMessagesItems");
		assertEquals(2, messagesListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		MenuMain menuMain = menuMainFactoryForTest.newMenuMain();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		MenuMain menuMainCreated = new MenuMain();
		when(menuMainService.create(menuMain)).thenReturn(menuMainCreated); 
		
		// When
		String viewName = menuMainController.create(menuMain, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/menuMain/form/"+menuMain.getMenuMainId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(menuMainCreated, (MenuMain) modelMap.get("menuMain"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		MenuMain menuMain = menuMainFactoryForTest.newMenuMain();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = menuMainController.create(menuMain, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("menuMain/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(menuMain, (MenuMain) modelMap.get("menuMain"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/menuMain/create", modelMap.get("saveAction"));
		
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

		MenuMain menuMain = menuMainFactoryForTest.newMenuMain();
		
		Exception exception = new RuntimeException("test exception");
		when(menuMainService.create(menuMain)).thenThrow(exception);
		
		// When
		String viewName = menuMainController.create(menuMain, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("menuMain/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(menuMain, (MenuMain) modelMap.get("menuMain"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/menuMain/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "menuMain.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<MessagesListItem> messagesListItems = (List<MessagesListItem>) modelMap.get("listOfMessagesItems");
		assertEquals(2, messagesListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		MenuMain menuMain = menuMainFactoryForTest.newMenuMain();
		Integer menuMainId = menuMain.getMenuMainId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		MenuMain menuMainSaved = new MenuMain();
		menuMainSaved.setMenuMainId(menuMainId);
		when(menuMainService.update(menuMain)).thenReturn(menuMainSaved); 
		
		// When
		String viewName = menuMainController.update(menuMain, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/menuMain/form/"+menuMain.getMenuMainId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(menuMainSaved, (MenuMain) modelMap.get("menuMain"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		MenuMain menuMain = menuMainFactoryForTest.newMenuMain();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = menuMainController.update(menuMain, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("menuMain/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(menuMain, (MenuMain) modelMap.get("menuMain"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/menuMain/update", modelMap.get("saveAction"));
		
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

		MenuMain menuMain = menuMainFactoryForTest.newMenuMain();
		
		Exception exception = new RuntimeException("test exception");
		when(menuMainService.update(menuMain)).thenThrow(exception);
		
		// When
		String viewName = menuMainController.update(menuMain, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("menuMain/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(menuMain, (MenuMain) modelMap.get("menuMain"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/menuMain/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "menuMain.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<MessagesListItem> messagesListItems = (List<MessagesListItem>) modelMap.get("listOfMessagesItems");
		assertEquals(2, messagesListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		MenuMain menuMain = menuMainFactoryForTest.newMenuMain();
		Integer menuMainId = menuMain.getMenuMainId();
		
		// When
		String viewName = menuMainController.delete(redirectAttributes, menuMainId);
		
		// Then
		verify(menuMainService).delete(menuMainId);
		assertEquals("redirect:/menuMain", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		MenuMain menuMain = menuMainFactoryForTest.newMenuMain();
		Integer menuMainId = menuMain.getMenuMainId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(menuMainService).delete(menuMainId);
		
		// When
		String viewName = menuMainController.delete(redirectAttributes, menuMainId);
		
		// Then
		verify(menuMainService).delete(menuMainId);
		assertEquals("redirect:/menuMain", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "menuMain.error.delete", exception);
	}
	
	
}
