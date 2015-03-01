package com.nichesoft.test;

import com.nichesoft.bean.jpa.MessagesEntity;

public class MessagesEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public MessagesEntity newMessagesEntity() {

		Integer messageId = mockValues.nextInteger();

		MessagesEntity messagesEntity = new MessagesEntity();
		messagesEntity.setMessageId(messageId);
		return messagesEntity;
	}
	
}
