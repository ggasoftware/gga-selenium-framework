package com.epam.jditests.dataproviders;

import org.testng.annotations.DataProvider;

public class LinkDP {

	public final static String TEXT = "About";
	public final static String HREF = "http://ecse00100176.epam.com/page3.htm";
	
	// TODO testText wait add more cases
	@DataProvider(name = "waitText")
	public static Object[][] waitText() {
		return new Object[][] { 
			{ "About", TEXT },
			{ "bout", TEXT }, 
			{ "out", TEXT } };
	}

	// TODO testText match add more cases
	@DataProvider(name = "matchText")
	public static Object[][] matchText() {
		return new Object[][] { 
			{ "Abou.", TEXT } 
			};
	}
	
	@DataProvider(name = "waitRef")
	public static Object[][] waitRef() {
		return new Object[][] { 
			{ "page3.htm", HREF },
			{ "http://", HREF }
			};
	}
	
	@DataProvider(name = "matchRef")
	public static Object[][] matchRef() {
		return new Object[][] { 
			{ ".*htm", HREF } 
			};
	}
}
