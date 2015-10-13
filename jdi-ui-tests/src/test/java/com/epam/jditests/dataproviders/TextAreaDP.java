package com.epam.jditests.dataproviders;

import static com.epam.jditests.entities.User.DEFAULT_USER;

import org.testng.annotations.DataProvider;

public class TextAreaDP {
	public static final String TEXT = DEFAULT_USER.description;
	
	// TODO testTextArea wait add more cases
	@DataProvider(name = "waitText")
	public static Object[][] waitText() {
		return new Object[][] { 
			{ "escr", TEXT },
			};
	}
 
	// TODO testTextArea match add more cases
	@DataProvider(name = "matchText")
	public static Object[][] matchText() {
		return new Object[][] { 
			{ "Descr.*", TEXT } 
			};
	}
}
