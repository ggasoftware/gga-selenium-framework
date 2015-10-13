package com.epam.jditests.dataproviders;

import org.testng.annotations.DataProvider;

public class TextDP {
	// TODO testText wait add more cases
	@DataProvider(name = "waitText")
	public static Object[][] waitText() {
		return new Object[][]{
				{"Lorem ipsum dolor sit amet".toUpperCase()},
				{" enim ad minim veniam".toUpperCase()}
		};
	}
}
