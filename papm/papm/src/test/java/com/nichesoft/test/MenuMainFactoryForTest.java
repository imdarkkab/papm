package com.nichesoft.test;

import com.nichesoft.bean.MenuMain;

public class MenuMainFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public MenuMain newMenuMain() {

		Integer menuMainId = mockValues.nextInteger();

		MenuMain menuMain = new MenuMain();
		menuMain.setMenuMainId(menuMainId);
		return menuMain;
	}
	
}
