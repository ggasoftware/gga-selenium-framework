package com.epam.jdi_tests.tests.common;

import static com.epam.jdi_tests.BaseScenario.baseWait3TextMatchTest;
import static com.epam.jdi_tests.BaseScenario.baseWait3TextTest;
import static com.epam.jdi_tests.BaseScenario.baseWaitTextMatchTest;
import static com.epam.jdi_tests.BaseScenario.baseWaitTextTest;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.jdi_tests.BaseScenarioInput;
import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.dataproviders.DatePickerDP;
import com.epam.jdi_tests.enums.Preconditions;
import com.epam.jdi_tests.page_objects.EpamJDISite;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IDatePicker;

public class DatePickerTest extends InitTests {

	public DatePickerTest() {
		_onPage = Preconditions.DATES_PAGE;
	}

	@Override
	public IDatePicker textElement() throws Exception {
		return EpamJDISite.dates._datepicker;
	}

	@BeforeMethod
	public void before(final Method method) throws Exception {
		isInState(_onPage, method);
	}

	// WAIT
	@Test(dataProvider = "waitText", dataProviderClass = DatePickerDP.class)
	public void waitTextTest(final String contains, final String expected) {
		baseWaitTextTest(this, contains, expected);
	}

	@Test(dataProvider = "waitText", dataProviderClass = DatePickerDP.class)
	public void wait3TextTest(final String contains, final String expected) {
		baseWait3TextTest(this, contains, expected);
	}
	// !WAIT

	// MATCH
	@Test(dataProvider = "matchText", dataProviderClass = DatePickerDP.class)
	public void waitTextMatchTest(final String regex, final String expected) {
		baseWaitTextMatchTest(this, regex, expected);
	}

	@Test(dataProvider = "matchText", dataProviderClass = DatePickerDP.class)
	public void wait3TextMatchTest(final String regex, final String expected) {
		baseWait3TextMatchTest(this, regex, expected);
	}
	// !MATCH

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
		BaseScenarioInput.newInputTest(this, DatePickerDP.currentDate());
	}

	@Test
	public void clearTest() throws Exception {
		BaseScenarioInput.clearTest(this);
	}

	@Test
	public void multiKeyTest() throws Exception {
		BaseScenarioInput.multiKeyTest(this, DatePickerDP.currentDate());
	}
	// !INPUT

	@Test
	public void focusTest() throws Exception {
		BaseScenarioInput.focusTest(this, "test123");
	}
}
