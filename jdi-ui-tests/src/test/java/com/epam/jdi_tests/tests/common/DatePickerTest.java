package com.epam.jdi_tests.tests.common;

import com.epam.jdi_tests.InitTests;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IDatePicker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jdi_tests.CommonData.TEST_DATE;
import static com.epam.jdi_tests.enums.Preconditions.DATES_PAGE;
import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.dates;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.*;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.areEquals;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.isTrue;

public class DatePickerTest extends InitTests {
	public IDatePicker datePicker() { return dates.datepicker; }

	@BeforeMethod
	public void before(Method method) throws Exception {
		isInState(DATES_PAGE, method);
	}

	@Test
	public void getTextTest() {
		areEquals(datePicker().getText(), TEST_DATE);
	}
	@Test
	public void getValueTest() {
		areEquals(datePicker().getValue(), TEST_DATE);
	}
	public void waitTextTest() {
		areEquals(datePicker().waitText("09/09"), TEST_DATE);
	}

	//TODO ! contains

	@Test
	public void matchTextTest() {
		areEquals(datePicker().waitMatchText("([0-9]{2}[\\/]{1}){2}[0-9]{4}"), TEST_DATE);
	}
	//TODO ! match
	@Test
	public void wait3TextTest() {
		isInState(SUPPORT_PAGE);
		runParallel(DATES_PAGE::open);
		areEquals(datePicker().waitText("09/09"), TEST_DATE);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}
	@Test
	public void match3TextTest() {
		isInState(SUPPORT_PAGE);
		runParallel(DATES_PAGE::open);
		areEquals(datePicker().waitMatchText("([0-9]{2}[\\/]{1}){2}[0-9]{4}"), TEST_DATE);
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
