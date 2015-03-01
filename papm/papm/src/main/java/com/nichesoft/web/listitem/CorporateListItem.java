/*
 * Created on 27 �.�. 2558 ( Time 16:44:31 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.web.listitem;

import com.nichesoft.bean.Corporate;
import com.nichesoft.web.common.ListItem;

public class CorporateListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public CorporateListItem(Corporate corporate) {
		super();

		this.value = ""
			 + corporate.getCorporateId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = corporate.toString();
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String getLabel() {
		return label;
	}

}