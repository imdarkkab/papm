package com.nichesoft.test;

import com.nichesoft.bean.Language;

public class LanguageFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Language newLanguage() {

		Integer languageId = mockValues.nextInteger();

		Language language = new Language();
		language.setLanguageId(languageId);
		return language;
	}
	
}
