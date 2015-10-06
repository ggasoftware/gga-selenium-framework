package com.epam.jdi_tests.dataproviders;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.DataProvider;

public class DatePickerDP {
	@DataProvider(name = "inputText")
	public static Object[][] inputText() {
		return new Object[][] { 
			{ "12/13/2014", "12/13/2014" },
			{ "12/13/14", "12/13/14" },
			};
	}
	
	@DataProvider(name = "matchText")
	public static Object[][] matchText() {
		return new Object[][] { 
			{ "([0-9]{2}[\\/]{1}){2}[0-9]{4}", currentDate() },
			};
	}
	
	@DataProvider(name = "waitText")
	public static Object[][] waitText() {
		return new Object[][] { 
			{ currentYear(), currentDate() },
			};
	}

	private static String currentYear() {
		final Date myDate = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		final String myDateString = sdf.format(myDate).trim();
		return myDateString;
	}
	
	public static String currentDate() {
		final Date myDate = new Date();
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		final String myDateString = sdf.format(myDate).trim();
		return myDateString;
	}
}
