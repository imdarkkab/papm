package com.nichesoft.test;

import com.nichesoft.bean.jpa.MenuSubEntity;

public class MenuSubEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public MenuSubEntity newMenuSubEntity() {

		Integer menuSubIs = mockValues.nextInteger();

		MenuSubEntity menuSubEntity = new MenuSubEntity();
		menuSubEntity.setMenuSubIs(menuSubIs);
		return menuSubEntity;
	}
	
}
