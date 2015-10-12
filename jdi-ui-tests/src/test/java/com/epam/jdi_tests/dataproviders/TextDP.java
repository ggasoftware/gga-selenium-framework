package com.epam.jdi_tests.dataproviders;

import org.testng.annotations.DataProvider;

public class TextDP {
	
	public static final String TEXT = 
			("Lorem ipsum dolor sit amet, consectetur adipisicing elit,"
			+ " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
			+ " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris"
			+ " nisi ut aliquip ex ea commodo consequat Duis aute irure dolor in"
			+ " reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.").toUpperCase();
	 
	@DataProvider(name = "waitText")
	public static Object[][] waitText() {
		return new Object[][] { 
			{ "Lorem ipsum dolor sit amet".toUpperCase(), TEXT },
			{ " CILLUM DOLORE EU FUGIAT NULLA PARIATUR", TEXT }, 
			{ "ENIM AD MINIM VENIAM, QUIS NOSTRUD", TEXT } };
	}

	@DataProvider(name = "matchText")
	public static Object[][] matchText() {
		return new Object[][] { 
			{ ".* ipsum dolor sit amet.*".toUpperCase(), TEXT } 
			};
	}
}
