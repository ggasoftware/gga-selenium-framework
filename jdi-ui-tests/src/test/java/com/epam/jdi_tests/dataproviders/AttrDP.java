package com.epam.jdi_tests.dataproviders;

import org.testng.annotations.DataProvider;

public class AttrDP {

	@DataProvider(name = "attr")
	public static Object[][] matchText() {
		return new Object[][] { 
			{ "testAttr", "testAttrValue" },
			{ "numAttr", "123" }
			};
	}

}
