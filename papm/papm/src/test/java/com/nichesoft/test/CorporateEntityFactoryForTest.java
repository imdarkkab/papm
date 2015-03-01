package com.nichesoft.test;

import com.nichesoft.bean.jpa.CorporateEntity;

public class CorporateEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public CorporateEntity newCorporateEntity() {

		Integer corporateId = mockValues.nextInteger();

		CorporateEntity corporateEntity = new CorporateEntity();
		corporateEntity.setCorporateId(corporateId);
		return corporateEntity;
	}
	
}
