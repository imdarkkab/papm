package com.nichesoft.test;

import com.nichesoft.bean.jpa.MenuMainEntity;

public class MenuMainEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public MenuMainEntity newMenuMainEntity() {

		Integer menuMainId = mockValues.nextInteger();

		MenuMainEntity menuMainEntity = new MenuMainEntity();
		menuMainEntity.setMenuMainId(menuMainId);
		return menuMainEntity;
	}
	
}
