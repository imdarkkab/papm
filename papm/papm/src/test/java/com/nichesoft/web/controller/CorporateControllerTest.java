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
import com.nichesoft.bean.Corporate;
import com.nichesoft.test.CorporateFactoryForTest;

//--- Services 
import com.nichesoft.business.service.CorporateService;


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
public class CorporateControllerTest {
	
	@InjectMocks
	private CorporateController corporateController;
	@Mock
	private CorporateService corporateService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private CorporateFactoryForTest corporateFactoryForTest = new CorporateFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Corporate> list = new ArrayList<Corporate>();
		when(corporateService.findAll()).thenReturn(list);
		
		// When
		String viewName = corporateController.list(model);
		
		// Then
		assertEquals("corporate/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = corporateController.formForCreate(model);
		
		// Then
		assertEquals("corporate/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Corporate)modelMap.get("corporate")).getCorporateId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/corporate/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Corporate corporate = corporateFactoryForTest.newCorporate();
		Integer corporateId = corporate.getCorporateId();
		when(corporateService.findById(corporateId)).thenReturn(corporate);
		
		// When
		String viewName = corporateController.formForUpdate(model, corporateId);
		
		// Then
		assertEquals("corporate/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(corporate, (Corporate) modelMap.get("corporate"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/corporate/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Corporate corporate = corporateFactoryForTest.newCorporate();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Corporate corporateCreated = new Corporate();
		when(corporateService.create(corporate)).thenReturn(corporateCreated); 
		
		// When
		String viewName = corporateController.create(corporate, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/corporate/form/"+corporate.getCorporateId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(corporateCreated, (Corporate) modelMap.get("corporate"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Corporate corporate = corporateFactoryForTest.newCorporate();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = corporateController.create(corporate, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("corporate/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(corporate, (Corporate) modelMap.get("corporate"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/corporate/create", modelMap.get("saveAction"));
		
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

		Corporate corporate = corporateFactoryForTest.newCorporate();
		
		Exception exception = new RuntimeException("test exception");
		when(corporateService.create(corporate)).thenThrow(exception);
		
		// When
		String viewName = corporateController.create(corporate, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("corporate/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(corporate, (Corporate) modelMap.get("corporate"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/corporate/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "corporate.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Corporate corporate = corporateFactoryForTest.newCorporate();
		Integer corporateId = corporate.getCorporateId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Corporate corporateSaved = new Corporate();
		corporateSaved.setCorporateId(corporateId);
		when(corporateService.update(corporate)).thenReturn(corporateSaved); 
		
		// When
		String viewName = corporateController.update(corporate, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/corporate/form/"+corporate.getCorporateId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(corporateSaved, (Corporate) modelMap.get("corporate"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Corporate corporate = corporateFactoryForTest.newCorporate();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = corporateController.update(corporate, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("corporate/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(corporate, (Corporate) modelMap.get("corporate"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/corporate/update", modelMap.get("saveAction"));
		
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

		Corporate corporate = corporateFactoryForTest.newCorporate();
		
		Exception exception = new RuntimeException("test exception");
		when(corporateService.update(corporate)).thenThrow(exception);
		
		// When
		String viewName = corporateController.update(corporate, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("corporate/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(corporate, (Corporate) modelMap.get("corporate"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/corporate/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "corporate.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Corporate corporate = corporateFactoryForTest.newCorporate();
		Integer corporateId = corporate.getCorporateId();
		
		// When
		String viewName = corporateController.delete(redirectAttributes, corporateId);
		
		// Then
		verify(corporateService).delete(corporateId);
		assertEquals("redirect:/corporate", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Corporate corporate = corporateFactoryForTest.newCorporate();
		Integer corporateId = corporate.getCorporateId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(corporateService).delete(corporateId);
		
		// When
		String viewName = corporateController.delete(redirectAttributes, corporateId);
		
		// Then
		verify(corporateService).delete(corporateId);
		assertEquals("redirect:/corporate", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "corporate.error.delete", exception);
	}
	
	
}
