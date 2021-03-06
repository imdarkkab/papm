/*
 * Created on 27 �.�. 2558 ( Time 16:44:33 )
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
import com.nichesoft.bean.RolePermission;
import com.nichesoft.bean.Permission;
import com.nichesoft.bean.Role;

//--- Services 
import com.nichesoft.business.service.RolePermissionService;
import com.nichesoft.business.service.PermissionService;
import com.nichesoft.business.service.RoleService;

//--- List Items 
import com.nichesoft.web.listitem.PermissionListItem;
import com.nichesoft.web.listitem.RoleListItem;

/**
 * Spring MVC controller for 'RolePermission' management.
 */
@Controller
@RequestMapping("/rolePermission")
public class RolePermissionController extends AbstractController {

	//--- Variables names ( to be used in JSP with Expression Language )
	private static final String MAIN_ENTITY_NAME = "rolePermission";
	private static final String MAIN_LIST_NAME   = "list";

	//--- JSP pages names ( View name in the MVC model )
	private static final String JSP_FORM   = "rolePermission/form";
	private static final String JSP_LIST   = "rolePermission/list";

	//--- SAVE ACTION ( in the HTML form )
	private static final String SAVE_ACTION_CREATE   = "/rolePermission/create";
	private static final String SAVE_ACTION_UPDATE   = "/rolePermission/update";

	//--- Main entity service
	@Resource
    private RolePermissionService rolePermissionService; // Injected by Spring
	//--- Other service(s)
	@Resource
    private PermissionService permissionService; // Injected by Spring
	@Resource
    private RoleService roleService; // Injected by Spring

	//--------------------------------------------------------------------------------------
	/**
	 * Default constructor
	 */
	public RolePermissionController() {
		super(RolePermissionController.class, MAIN_ENTITY_NAME );
		log("RolePermissionController created.");
	}

	//--------------------------------------------------------------------------------------
	// Spring MVC model management
	//--------------------------------------------------------------------------------------
	/**
	 * Populates the combo-box "items" for the referenced entity "Permission"
	 * @param model
	 */
	private void populateListOfPermissionItems(Model model) {
		List<Permission> list = permissionService.findAll();
		List<PermissionListItem> items = new LinkedList<PermissionListItem>();
		for ( Permission permission : list ) {
			items.add(new PermissionListItem( permission ) );
		}
		model.addAttribute("listOfPermissionItems", items ) ;
	}

	/**
	 * Populates the combo-box "items" for the referenced entity "Role"
	 * @param model
	 */
	private void populateListOfRoleItems(Model model) {
		List<Role> list = roleService.findAll();
		List<RoleListItem> items = new LinkedList<RoleListItem>();
		for ( Role role : list ) {
			items.add(new RoleListItem( role ) );
		}
		model.addAttribute("listOfRoleItems", items ) ;
	}


	/**
	 * Populates the Spring MVC model with the given entity and eventually other useful data
	 * @param model
	 * @param rolePermission
	 */
	private void populateModel(Model model, RolePermission rolePermission, FormMode formMode) {
		//--- Main entity
		model.addAttribute(MAIN_ENTITY_NAME, rolePermission);
		if ( formMode == FormMode.CREATE ) {
			model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE); 			
			//--- Other data useful in this screen in "create" mode (all fields)
			populateListOfPermissionItems(model);
			populateListOfRoleItems(model);
		}
		else if ( formMode == FormMode.UPDATE ) {
			model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE); 			
			//--- Other data useful in this screen in "update" mode (only non-pk fields)
			populateListOfRoleItems(model);
			populateListOfPermissionItems(model);
		}
	}

	//--------------------------------------------------------------------------------------
	// Request mapping
	//--------------------------------------------------------------------------------------
	/**
	 * Shows a list with all the occurrences of RolePermission found in the database
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping()
	public String list(Model model) {
		log("Action 'list'");
		List<RolePermission> list = rolePermissionService.findAll();
		model.addAttribute(MAIN_LIST_NAME, list);		
		return JSP_LIST;
	}

	/**
	 * Shows a form page in order to create a new RolePermission
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping("/form")
	public String formForCreate(Model model) {
		log("Action 'formForCreate'");
		//--- Populates the model with a new instance
		RolePermission rolePermission = new RolePermission();	
		populateModel( model, rolePermission, FormMode.CREATE);
		return JSP_FORM;
	}

	/**
	 * Shows a form page in order to update an existing RolePermission
	 * @param model Spring MVC model
	 * @param rolePermissionId  primary key element
	 * @return
	 */
	@RequestMapping(value = "/form/{rolePermissionId}")
	public String formForUpdate(Model model, @PathVariable("rolePermissionId") Integer rolePermissionId ) {
		log("Action 'formForUpdate'");
		//--- Search the entity by its primary key and stores it in the model 
		RolePermission rolePermission = rolePermissionService.findById(rolePermissionId);
		populateModel( model, rolePermission, FormMode.UPDATE);		
		return JSP_FORM;
	}

	/**
	 * 'CREATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param rolePermission  entity to be created
	 * @param bindingResult Spring MVC binding result
	 * @param model Spring MVC model
	 * @param redirectAttributes Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/create" ) // GET or POST
	public String create(@Valid RolePermission rolePermission, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		log("Action 'create'");
		try {
			if (!bindingResult.hasErrors()) {
				RolePermission rolePermissionCreated = rolePermissionService.create(rolePermission); 
				model.addAttribute(MAIN_ENTITY_NAME, rolePermissionCreated);

				//---
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				return redirectToForm(httpServletRequest, rolePermission.getRolePermissionId() );
			} else {
				populateModel( model, rolePermission, FormMode.CREATE);
				return JSP_FORM;
			}
		} catch(Exception e) {
			log("Action 'create' : Exception - " + e.getMessage() );
			messageHelper.addException(model, "rolePermission.error.create", e);
			populateModel( model, rolePermission, FormMode.CREATE);
			return JSP_FORM;
		}
	}

	/**
	 * 'UPDATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param rolePermission  entity to be updated
	 * @param bindingResult Spring MVC binding result
	 * @param model Spring MVC model
	 * @param redirectAttributes Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/update" ) // GET or POST
	public String update(@Valid RolePermission rolePermission, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		log("Action 'update'");
		try {
			if (!bindingResult.hasErrors()) {
				//--- Perform database operations
				RolePermission rolePermissionSaved = rolePermissionService.update(rolePermission);
				model.addAttribute(MAIN_ENTITY_NAME, rolePermissionSaved);
				//--- Set the result message
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				log("Action 'update' : update done - redirect");
				return redirectToForm(httpServletRequest, rolePermission.getRolePermissionId());
			} else {
				log("Action 'update' : binding errors");
				populateModel( model, rolePermission, FormMode.UPDATE);
				return JSP_FORM;
			}
		} catch(Exception e) {
			messageHelper.addException(model, "rolePermission.error.update", e);
			log("Action 'update' : Exception - " + e.getMessage() );
			populateModel( model, rolePermission, FormMode.UPDATE);
			return JSP_FORM;
		}
	}

	/**
	 * 'DELETE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param redirectAttributes
	 * @param rolePermissionId  primary key element
	 * @return
	 */
	@RequestMapping(value = "/delete/{rolePermissionId}") // GET or POST
	public String delete(RedirectAttributes redirectAttributes, @PathVariable("rolePermissionId") Integer rolePermissionId) {
		log("Action 'delete'" );
		try {
			rolePermissionService.delete( rolePermissionId );
			//--- Set the result message
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));	
		} catch(Exception e) {
			messageHelper.addException(redirectAttributes, "rolePermission.error.delete", e);
		}
		return redirectToList();
	}

}
