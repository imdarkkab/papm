package com.nichesoft.test;

import com.nichesoft.bean.Corporate;

public class CorporateFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Corporate newCorporate() {

		Integer corporateId = mockValues.nextInteger();

		Corporate corporate = new Corporate();
		corporate.setCorporateId(corporateId);
		return corporate;
	}
	
}
