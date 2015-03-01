/*
 * Created on 27 �.�. 2558 ( Time 16:44:32 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.web.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//--- Common classes
import com.nichesoft.web.common.AbstractController;
import com.nichesoft.web.common.FormMode;
import com.nichesoft.web.common.Message;
import com.nichesoft.web.common.MessageType;

//--- Entities
import com.nichesoft.bean.Messages;
import com.nichesoft.bean.Language;

//--- Services 
import com.nichesoft.business.service.MessagesService;
import com.nichesoft.business.service.LanguageService;

//--- List Items 
import com.nichesoft.web.listitem.LanguageListItem;

/**
 * Spring MVC controller for 'Messages' management.
 */
@Controller
@RequestMapping("/messages")
public class MessagesController extends AbstractController {

	//--- Variables names ( to be used in JSP with Expression Language )
	private static final String MAIN_ENTITY_NAME = "messages";
	private static final String MAIN_LIST_NAME   = "list";

	//--- JSP pages names ( View name in the MVC model )
	private static final String JSP_FORM   = "messages/form";
	private static final String JSP_LIST   = "messages/list";

	//--- SAVE ACTION ( in the HTML form )
	private static final String SAVE_ACTION_CREATE   = "/messages/create";
	private static final String SAVE_ACTION_UPDATE   = "/messages/update";

	//--- Main entity service
	@Resource
    private MessagesService messagesService; // Injected by Spring
	//--- Other service(s)
	@Resource
    private LanguageService languageService; // Injected by Spring

	//--------------------------------------------------------------------------------------
	/**
	 * Default constructor
	 */
	public MessagesController() {
		super(MessagesController.class, MAIN_ENTITY_NAME );
		log("MessagesController created.");
	}

	//--------------------------------------------------------------------------------------
	// Spring MVC model management
	//--------------------------------------------------------------------------------------
	/**
	 * Populates the combo-box "items" for the referenced entity "Language"
	 * @param model
	 */
	private void populateListOfLanguageItems(Model model) {
		List<Language> list = languageService.findAll();
		List<LanguageListItem> items = new LinkedList<LanguageListItem>();
		for ( Language language : list ) {
			items.add(new LanguageListItem( language ) );
		}
		model.addAttribute("listOfLanguageItems", items ) ;
	}


	/**
	 * Populates the Spring MVC model with the given entity and eventually other useful data
	 * @param model
	 * @param messages
	 */
	private void populateModel(Model model, Messages messages, FormMode formMode) {
		//--- Main entity
		model.addAttribute(MAIN_ENTITY_NAME, messages);
		if ( formMode == FormMode.CREATE ) {
			model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE); 			
			//--- Other data useful in this screen in "create" mode (all fields)
			populateListOfLanguageItems(model);
		}
		else if ( formMode == FormMode.UPDATE ) {
			model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE); 			
			//--- Other data useful in this screen in "update" mode (only non-pk fields)
			populateListOfLanguageItems(model);
		}
	}

	//--------------------------------------------------------------------------------------
	// Request mapping
	//--------------------------------------------------------------------------------------
	/**
	 * Shows a list with all the occurrences of Messages found in the database
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping()
	public String list(Model model) {
		log("Action 'list'");
		List<Messages> list = messagesService.findAll();
		model.addAttribute(MAIN_LIST_NAME, list);		
		return JSP_LIST;
	}

	/**
	 * Shows a form page in order to create a new Messages
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping("/form")
	public String formForCreate(Model model) {
		log("Action 'formForCreate'");
		//--- Populates the model with a new instance
		Messages messages = new Messages();	
		populateModel( model, messages, FormMode.CREATE);
		return JSP_FORM;
	}

	/**
	 * Shows a form page in order to update an existing Messages
	 * @param model Spring MVC model
	 * @param messageId  primary key element
	 * @return
	 */
	@RequestMapping(value = "/form/{messageId}")
	public String formForUpdate(Model model, @PathVariable("messageId") Integer messageId ) {
		log("Action 'formForUpdate'");
		//--- Search the entity by its primary key and stores it in the model 
		Messages messages = messagesService.findById(messageId);
		populateModel( model, messages, FormMode.UPDATE);		
		return JSP_FORM;
	}

	/**
	 * 'CREATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param messages  entity to be created
	 * @param bindingResult Spring MVC binding result
	 * @param model Spring MVC model
	 * @param redirectAttributes Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/create" ) // GET or POST
	public String create(@Valid Messages messages, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		log("Action 'create'");
		try {
			if (!bindingResult.hasErrors()) {
				Messages messagesCreated = messagesService.create(messages); 
				model.addAttribute(MAIN_ENTITY_NAME, messagesCreated);

				//---
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				return redirectToForm(httpServletRequest, messages.getMessageId() );
			} else {
				populateModel( model, messages, FormMode.CREATE);
				return JSP_FORM;
			}
		} catch(Exception e) {
			log("Action 'create' : Exception - " + e.getMessage() );
			messageHelper.addException(model, "messages.error.create", e);
			populateModel( model, messages, FormMode.CREATE);
			return JSP_FORM;
		}
	}

	/**
	 * 'UPDATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param messages  entity to be updated
	 * @param bindingResult Spring MVC binding result
	 * @param model Spring MVC model
	 * @param redirectAttributes Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/update" ) // GET or POST
	public String update(@Valid Messages messages, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		log("Action 'update'");
		try {
			if (!bindingResult.hasErrors()) {
				//--- Perform database operations
				Messages messagesSaved = messagesService.update(messages);
				model.addAttribute(MAIN_ENTITY_NAME, messagesSaved);
				//--- Set the result message
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				log("Action 'update' : update done - redirect");
				return redirectToForm(httpServletRequest, messages.getMessageId());
			} else {
				log("Action 'update' : binding errors");
				populateModel( model, messages, FormMode.UPDATE);
				return JSP_FORM;
			}
		} catch(Exception e) {
			messageHelper.addException(model, "messages.error.update", e);
			log("Action 'update' : Exception - " + e.getMessage() );
			populateModel( model, messages, FormMode.UPDATE);
			return JSP_FORM;
		}
	}

	/**
	 * 'DELETE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param redirectAttributes
	 * @param messageId  primary key element
	 * @return
	 */
	@RequestMapping(value = "/delete/{messageId}") // GET or POST
	public String delete(RedirectAttributes redirectAttributes, @PathVariable("messageId") Integer messageId) {
		log("Action 'delete'" );
		try {
			messagesService.delete( messageId );
			//--- Set the result message
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));	
		} catch(Exception e) {
			messageHelper.addException(redirectAttributes, "messages.error.delete", e);
		}
		return redirectToList();
	}

}