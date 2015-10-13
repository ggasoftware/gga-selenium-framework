package com.epam.jditests.dataproviders;

import static com.epam.jditests.entities.User.DEFAULT_USER;

import org.testng.annotations.DataProvider;

public class TextFieldDP {
	public static final String TEXT = DEFAULT_USER.name;
	
	@DataProvider(name = "waitText")
	public static Object[][] waitText() {
		return new Object[][] { 
			{ "ame", TEXT },
			};
	} 

	@DataProvider(name = "matchText")
	public static Object[][] matchText() {
		return new Object[][] { 
			{ ".am.", TEXT } 
			};
	}
	
	@DataProvider(name = "inputText")
	public static Object[][] inputText() {
		return new Object[][] { 
			{ "testString", "testString" },
			{ "123test", "123test" },
			{ "1234567890", "1234567890" },
			{ "\"", "\"" },
			{"!@#^&*()_", "!@#^&*()_"},
			{ "   ", "   " },
			{ "TEST", "TEST" },
			};
	}
}
