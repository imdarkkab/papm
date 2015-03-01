/*
 * Created on 27 �.�. 2558 ( Time 16:44:32 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.web.listitem;

import com.nichesoft.bean.Messages;
import com.nichesoft.web.common.ListItem;

public class MessagesListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public MessagesListItem(Messages messages) {
		super();

		this.value = ""
			 + messages.getMessageId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = messages.toString();
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
