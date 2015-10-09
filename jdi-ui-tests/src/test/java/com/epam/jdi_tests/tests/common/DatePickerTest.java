package com.epam.jdi_tests.tests.common;

import com.epam.jdi_tests.InitTests;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IDatePicker;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jdi_tests.enums.Preconditions.DATES_PAGE;
import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.dates;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.*;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.getDriver;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.isTrue;

public class DatePickerTest extends InitTests {
	public IDatePicker datePicker() { return dates._datepicker; }

	@BeforeMethod
	public void before(Method method) throws Exception {
		isInState(DATES_PAGE, method);
		WebElement datePicker = getDriver().findElement(datePicker().getLocator());
		datePicker.clear();
		datePicker.sendKeys(TEST_DATE);
	}

	@Test
	public void getTextTest() {
		checkText(() -> datePicker().getText(), TEST_DATE);
	}
	@Test
	public void getValueTest() {
		checkText(() -> datePicker().getValue(), TEST_DATE);
	}
	public void waitTextTest() {
		checkText(() -> datePicker().waitText("09/09"), TEST_DATE);
	}

	public static final String TEST_DATE = "09/09/1945";
	//TODO ! contains

	@Test
	public void matchTextTest() {
		checkText(() -> datePicker().waitMatchText("([0-9]{2}[\\/]{1}){2}[0-9]{4}"), TEST_DATE);
	}
	//TODO ! match
	@Test
	public void wait3TextTest() {
		isInState(SUPPORT_PAGE);
		runParallel(dates::open);
		checkText(() -> datePicker().waitText("09/09"), TEST_DATE);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}
	@Test
	public void match3TextTest() {
		isInState(SUPPORT_PAGE);
		runParallel(dates::open);
		checkText(() -> datePicker().waitMatchText("([0-9]{2}[\\/]{1}){2}[0-9]{4}"), TEST_DATE);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}

/*
TODO
	// INPUT
	@Test(dataProvider = "inputText", dataProviderClass = DatePickerDP.class)
	public void inputTest(final String in, final String expected) throws Exception {
		BaseScenarioInput.inputTest(this, in, expected);
	}

	@Test(dataProvider = "inputText", dataProviderClass = DatePickerDP.class)
	public void sendKeysTest(final String in, final String expected) throws Exception {
		BaseScenarioInput.sendKeysTest(this, in, expected);
	}

	@Test
	public void newInputTest() throws Exception {
		BaseScenarioInput.newInputTest(this, Timer.nowTime("MM/dd/yyyy"));
	}

	@Test
	public void clearTest() throws Exception {
		BaseScenarioInput.clearTest(this);
	}

	@Test
	public void multiKeyTest() throws Exception {
		BaseScenarioInput.multiKeyTest(this, Timer.nowTime("MM/dd/yyyy"));
	}
	// !INPUT

	@Test
	public void focusTest() throws Exception {
		BaseScenarioInput.focusTest(this, "test123");
	}*/
}
