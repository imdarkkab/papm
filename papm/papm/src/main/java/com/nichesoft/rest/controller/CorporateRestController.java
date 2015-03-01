/*
 * Created on 27 �.�. 2558 ( Time 16:44:32 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.rest.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.nichesoft.bean.Corporate;
import com.nichesoft.business.service.CorporateService;
import com.nichesoft.web.listitem.CorporateListItem;

/**
 * Spring MVC controller for 'Corporate' management.
 */
@Controller
public class CorporateRestController {

	@Resource
	private CorporateService corporateService;
	
	@RequestMapping( value="/items/corporate",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<CorporateListItem> findAllAsListItems() {
		List<Corporate> list = corporateService.findAll();
		List<CorporateListItem> items = new LinkedList<CorporateListItem>();
		for ( Corporate corporate : list ) {
			items.add(new CorporateListItem( corporate ) );
		}
		return items;
	}
	
	@RequestMapping( value="/corporate",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Corporate> findAll() {
		return corporateService.findAll();
	}

	@RequestMapping( value="/corporate/{corporateId}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Corporate findOne(@PathVariable("corporateId") Integer corporateId) {
		return corporateService.findById(corporateId);
	}
	
	@RequestMapping( value="/corporate",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Corporate create(@RequestBody Corporate corporate) {
		return corporateService.create(corporate);
	}

	@RequestMapping( value="/corporate/{corporateId}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Corporate update(@PathVariable("corporateId") Integer corporateId, @RequestBody Corporate corporate) {
		return corporateService.update(corporate);
	}

	@RequestMapping( value="/corporate/{corporateId}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("corporateId") Integer corporateId) {
		corporateService.delete(corporateId);
	}
	
}