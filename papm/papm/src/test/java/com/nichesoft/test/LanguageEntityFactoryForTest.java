package com.nichesoft.test;

import com.nichesoft.bean.jpa.LanguageEntity;

public class LanguageEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public LanguageEntity newLanguageEntity() {

		Integer languageId = mockValues.nextInteger();

		LanguageEntity languageEntity = new LanguageEntity();
		languageEntity.setLanguageId(languageId);
		return languageEntity;
	}
	
}
