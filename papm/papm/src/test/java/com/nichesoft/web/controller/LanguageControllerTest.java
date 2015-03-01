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
import com.nichesoft.bean.Language;
import com.nichesoft.test.LanguageFactoryForTest;

//--- Services 
import com.nichesoft.business.service.LanguageService;


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
public class LanguageControllerTest {
	
	@InjectMocks
	private LanguageController languageController;
	@Mock
	private LanguageService languageService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private LanguageFactoryForTest languageFactoryForTest = new LanguageFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Language> list = new ArrayList<Language>();
		when(languageService.findAll()).thenReturn(list);
		
		// When
		String viewName = languageController.list(model);
		
		// Then
		assertEquals("language/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = languageController.formForCreate(model);
		
		// Then
		assertEquals("language/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Language)modelMap.get("language")).getLanguageId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/language/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Language language = languageFactoryForTest.newLanguage();
		Integer languageId = language.getLanguageId();
		when(languageService.findById(languageId)).thenReturn(language);
		
		// When
		String viewName = languageController.formForUpdate(model, languageId);
		
		// Then
		assertEquals("language/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(language, (Language) modelMap.get("language"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/language/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Language language = languageFactoryForTest.newLanguage();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Language languageCreated = new Language();
		when(languageService.create(language)).thenReturn(languageCreated); 
		
		// When
		String viewName = languageController.create(language, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/language/form/"+language.getLanguageId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(languageCreated, (Language) modelMap.get("language"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Language language = languageFactoryForTest.newLanguage();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = languageController.create(language, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("language/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(language, (Language) modelMap.get("language"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/language/create", modelMap.get("saveAction"));
		
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

		Language language = languageFactoryForTest.newLanguage();
		
		Exception exception = new RuntimeException("test exception");
		when(languageService.create(language)).thenThrow(exception);
		
		// When
		String viewName = languageController.create(language, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("language/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(language, (Language) modelMap.get("language"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/language/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "language.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Language language = languageFactoryForTest.newLanguage();
		Integer languageId = language.getLanguageId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Language languageSaved = new Language();
		languageSaved.setLanguageId(languageId);
		when(languageService.update(language)).thenReturn(languageSaved); 
		
		// When
		String viewName = languageController.update(language, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/language/form/"+language.getLanguageId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(languageSaved, (Language) modelMap.get("language"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Language language = languageFactoryForTest.newLanguage();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = languageController.update(language, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("language/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(language, (Language) modelMap.get("language"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/language/update", modelMap.get("saveAction"));
		
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

		Language language = languageFactoryForTest.newLanguage();
		
		Exception exception = new RuntimeException("test exception");
		when(languageService.update(language)).thenThrow(exception);
		
		// When
		String viewName = languageController.update(language, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("language/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(language, (Language) modelMap.get("language"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/language/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "language.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Language language = languageFactoryForTest.newLanguage();
		Integer languageId = language.getLanguageId();
		
		// When
		String viewName = languageController.delete(redirectAttributes, languageId);
		
		// Then
		verify(languageService).delete(languageId);
		assertEquals("redirect:/language", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Language language = languageFactoryForTest.newLanguage();
		Integer languageId = language.getLanguageId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(languageService).delete(languageId);
		
		// When
		String viewName = languageController.delete(redirectAttributes, languageId);
		
		// Then
		verify(languageService).delete(languageId);
		assertEquals("redirect:/language", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "language.error.delete", exception);
	}
	
	
}
