package com.nichesoft.test;

import com.nichesoft.bean.MenuSub;

public class MenuSubFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public MenuSub newMenuSub() {

		Integer menuSubIs = mockValues.nextInteger();

		MenuSub menuSub = new MenuSub();
		menuSub.setMenuSubIs(menuSubIs);
		return menuSub;
	}
	
}
