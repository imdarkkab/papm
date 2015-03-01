/*
 * Created on 27 �.�. 2558 ( Time 16:44:19 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.nichesoft.bean.Messages;
import com.nichesoft.bean.jpa.MessagesEntity;
import com.nichesoft.bean.jpa.LanguageEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class MessagesServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public MessagesServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'MessagesEntity' to 'Messages'
	 * @param messagesEntity
	 */
	public Messages mapMessagesEntityToMessages(MessagesEntity messagesEntity) {
		if(messagesEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Messages messages = map(messagesEntity, Messages.class);

		//--- Link mapping ( link to Language )
		if(messagesEntity.getLanguage() != null) {
			messages.setLanguageId(messagesEntity.getLanguage().getLanguageId());
		}
		return messages;
	}
	
	/**
	 * Mapping from 'Messages' to 'MessagesEntity'
	 * @param messages
	 * @param messagesEntity
	 */
	public void mapMessagesToMessagesEntity(Messages messages, MessagesEntity messagesEntity) {
		if(messages == null) {
			return;
		}

		//--- Generic mapping 
		map(messages, messagesEntity);

		//--- Link mapping ( link : messages )
		if( hasLinkToLanguage(messages) ) {
			LanguageEntity language1 = new LanguageEntity();
			language1.setLanguageId( messages.getLanguageId() );
			messagesEntity.setLanguage( language1 );
		} else {
			messagesEntity.setLanguage( null );
		}

	}
	
	/**
	 * Verify that Language id is valid.
	 * @param Language Language
	 * @return boolean
	 */
	private boolean hasLinkToLanguage(Messages messages) {
		if(messages.getLanguageId() != null) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}