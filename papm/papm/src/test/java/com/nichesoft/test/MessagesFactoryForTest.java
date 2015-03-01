package com.nichesoft.test;

import com.nichesoft.bean.Messages;

public class MessagesFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Messages newMessages() {

		Integer messageId = mockValues.nextInteger();

		Messages messages = new Messages();
		messages.setMessageId(messageId);
		return messages;
	}
	
}
