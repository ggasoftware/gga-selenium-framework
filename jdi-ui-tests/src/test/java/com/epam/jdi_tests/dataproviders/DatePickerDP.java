package com.epam.jdi_tests.dataproviders;
import org.testng.annotations.DataProvider;
import com.ggasoftware.jdi_ui_tests.core.utils.common.Timer;

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
			{ "([0-9]{2}[\\/]{1}){2}[0-9]{4}", Timer.nowTime("MM/dd/yyyy") },
			};
	}
	
	@DataProvider(name = "waitText")
	public static Object[][] waitText() {
		return new Object[][] { 
			{ Timer.nowTime("yyyy"), Timer.nowTime("MM/dd/yyyy") },
			};
	}
}
