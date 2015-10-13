package com.epam.jditests.dataproviders;

import org.testng.annotations.DataProvider;

public class LabelDP {
	public static final String TEXT = "CALCULATE";

	// TODO testLabel wait add more cases
	@DataProvider(name = "waitText")
	public static Object[][] waitText() {
		return new Object[][] { 
			{ "CUL", TEXT }, 
			{ "LATE", TEXT }
			};

	}
 
	// TODO testLabel match add more cases
	@DataProvider(name = "matchText")
	public static Object[][] matchText() {
		return new Object[][] { 
			{ "C.*C.LATE", TEXT },
			{ ".*LCU.*", TEXT },
			{ ".*LCU.*", TEXT },
			{ "[a-zA-Z]{9}", TEXT }
			};
	}
}
