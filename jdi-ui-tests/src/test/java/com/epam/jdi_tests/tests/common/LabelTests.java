package com.epam.jdi_tests.tests.common;

import static com.epam.jdi_tests.BaseScenario.baseGetTextTest;
import static com.epam.jdi_tests.BaseScenario.baseWait3TextMatchTest;
import static com.epam.jdi_tests.BaseScenario.baseWait3TextTest;
import static com.epam.jdi_tests.BaseScenario.baseWaitTextMatchTest;
import static com.epam.jdi_tests.BaseScenario.baseWaitTextTest;
import static com.epam.jdi_tests.dataproviders.LabelDP.TEXT;
import static com.epam.jdi_tests.enums.Preconditions.METALS_AND_COLORS_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.page_objects.EpamJDISite.metalsColorsPage;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkCalculate;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkText;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.jdi_tests.BaseScenario;
import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.dataproviders.AttrDP;
import com.epam.jdi_tests.dataproviders.LabelDP;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.ILabel;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class LabelTests extends InitTests {

	@Override
	public ILabel textElement() {
		return metalsColorsPage.calculate;
	}

	public LabelTests() {
		_onPage = METALS_AND_COLORS_PAGE;
	}
	
	@BeforeMethod
	public void before(final Method method) throws IOException {
		isInState(_onPage, method);
	}

	@Test
	public void textWaitTestWithButton() {
		textElement().click();
		checkCalculate("Summary: 3");
	}
 
	// WAIT
	@Test(dataProvider = "waitText", dataProviderClass = LabelDP.class)
	public void waitTextTest(final String contains, final String expected) {
		baseWaitTextTest(this, contains, expected);
	}

	@Test(dataProvider = "waitText", dataProviderClass = LabelDP.class)
	public void wait3TextTest(final String contains, final String expected) {
		baseWait3TextTest(this, contains, expected);
	}
	// !WAIT

	// MATCH
	@Test(dataProvider = "matchText", dataProviderClass = LabelDP.class)
	public void waitTextMatchTest(final String regex, final String expected) {
		baseWaitTextMatchTest(this, regex, expected);
	}

	@Test(dataProvider = "matchText", dataProviderClass = LabelDP.class)
	public void wait3TextMatchTest(final String regex, final String expected) {
		baseWait3TextMatchTest(this, regex, expected);
	}
	// !MATCH

	@Test(dataProvider = "attr", dataProviderClass = AttrDP.class)
	public void setAttrTest(final String attrName, final String attrValue) throws Exception {
		BaseScenario.baseSetAttrTest(this, attrName, attrValue);
	}
	
	@Test
	public void getTextTest() {
		baseGetTextTest(this, TEXT);
	}

	@Test
	public void getValueTest() {
		checkText(textElement()::getValue, TEXT);
	}
}
