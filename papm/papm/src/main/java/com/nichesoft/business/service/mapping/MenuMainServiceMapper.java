/*
 * Created on 27 �.�. 2558 ( Time 16:44:19 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.nichesoft.bean.MenuMain;
import com.nichesoft.bean.jpa.MenuMainEntity;
import com.nichesoft.bean.jpa.MessagesEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class MenuMainServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public MenuMainServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'MenuMainEntity' to 'MenuMain'
	 * @param menuMainEntity
	 */
	public MenuMain mapMenuMainEntityToMenuMain(MenuMainEntity menuMainEntity) {
		if(menuMainEntity == null) {
			return null;
		}

		//--- Generic mapping 
		MenuMain menuMain = map(menuMainEntity, MenuMain.class);

		//--- Link mapping ( link to Messages )
		if(menuMainEntity.getMessages() != null) {
			menuMain.setMessageId(menuMainEntity.getMessages().getMessageId());
		}
		return menuMain;
	}
	
	/**
	 * Mapping from 'MenuMain' to 'MenuMainEntity'
	 * @param menuMain
	 * @param menuMainEntity
	 */
	public void mapMenuMainToMenuMainEntity(MenuMain menuMain, MenuMainEntity menuMainEntity) {
		if(menuMain == null) {
			return;
		}

		//--- Generic mapping 
		map(menuMain, menuMainEntity);

		//--- Link mapping ( link : menuMain )
		if( hasLinkToMessages(menuMain) ) {
			MessagesEntity messages1 = new MessagesEntity();
			messages1.setMessageId( menuMain.getMessageId() );
			menuMainEntity.setMessages( messages1 );
		} else {
			menuMainEntity.setMessages( null );
		}

	}
	
	/**
	 * Verify that Messages id is valid.
	 * @param Messages Messages
	 * @return boolean
	 */
	private boolean hasLinkToMessages(MenuMain menuMain) {
		if(menuMain.getMessageId() != null) {
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