package com.epam.jdi_tests.tests.common;

import static com.epam.jdi_tests.BaseScenario.baseGetTextTest;
import static com.epam.jdi_tests.BaseScenario.baseGetValueTest;
import static com.epam.jdi_tests.BaseScenario.baseWait3TextMatchTest;
import static com.epam.jdi_tests.BaseScenario.baseWait3TextTest;
import static com.epam.jdi_tests.BaseScenario.baseWaitTextMatchTest;
import static com.epam.jdi_tests.BaseScenario.baseWaitTextTest;
import static com.epam.jdi_tests.dataproviders.TextDP.TEXT;
import static com.epam.jdi_tests.enums.Preconditions.HOME_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.homePage;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.jdi_tests.BaseScenario;
import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.dataproviders.AttrDP;
import com.epam.jdi_tests.dataproviders.TextDP;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IText;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class TextTests extends InitTests {

	public TextTests() {
		_onPage = HOME_PAGE;
	}
 
	@Override
	public IText textElement() {
		return homePage.text;
	}

	@BeforeMethod
	public void before(final Method method) throws IOException {
		isInState(_onPage, method);
	}

	// WAIT
	@Test(dataProvider = "waitText", dataProviderClass = TextDP.class)
	public void waitTextTest(final String contains, final String expected) {
		baseWaitTextTest(this, contains, expected);
	}

	@Test(dataProvider = "waitText", dataProviderClass = TextDP.class)
	public void wait3TextTest(final String contains, final String expected) {
		baseWait3TextTest(this, contains, expected);
	}
	// !WAIT

	// MATCH
	@Test(dataProvider = "matchText", dataProviderClass = TextDP.class)
	public void waitTextMatchTest(final String regex, final String expected) {
		baseWaitTextMatchTest(this, regex, expected);
	}

	@Test(dataProvider = "matchText", dataProviderClass = TextDP.class)
	public void wait3TextMatchTest(final String regex, final String expected) {
		baseWait3TextMatchTest(this, regex, expected);
	}
	// !MATCH

	@Test
	public void getTextTest() {
		baseGetTextTest(this, TEXT);
	}

	@Test
	public void getValueTest() {
		baseGetValueTest(this, TEXT);
	}
	
	@Test(dataProvider = "attr", dataProviderClass = AttrDP.class)
	public void setAttrTest(final String attrName, final String attrValue) throws Exception {
		BaseScenario.baseSetAttrTest(this, attrName, attrValue);
	}
}
