package com.epam.jdi_tests.tests.common;

import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.dataproviders.TextDP;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IText;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jdi_tests.enums.Preconditions.HOME_PAGE;
import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.homePage;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.runParallel;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.waitTimeOut;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.areEquals;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.isTrue;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class TextTests extends InitTests {

	private IText text = homePage.text;
	public TextTests(IText text) {
		this.text = text;
	}

	public IText text() {
		return text;
	}

	@BeforeMethod
	public void before(final Method method) throws IOException {
		isInState(HOME_PAGE, method);
	}
	@Test
	public void getTextTest() {
		areEquals(text().getText(), TEXT);
	}
	@Test
	public void getValueTest() {
		areEquals(text().getValue(), TEXT);
	}
	@Test(dataProvider = "waitText", dataProviderClass = TextDP.class)
	public void waitTextTest(String contains) {
		areEquals(text().waitText(contains), TEXT);
	}

	//TODO ! contains

	@Test
	public void matchTextTest() {
		areEquals(() -> text().waitMatchText(".* ipsum dolor sit amet.*".toUpperCase()), TEXT);
	}
	//TODO ! match
	@Test
	public void wait3TextTest() {
		isInState(SUPPORT_PAGE);
		runParallel(homePage::open);
		areEquals(text().waitText("Lorem ipsum dolor sit amet".toUpperCase()), TEXT);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}

	@Test
	public void match3TextTest() {
		areEquals(text().waitMatchText(".* ipsum dolor sit amet.*".toUpperCase()), TEXT);
	}

	public static final String TEXT =
			("Lorem ipsum dolor sit amet, consectetur adipisicing elit,"
					+ " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
					+ " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris"
					+ " nisi ut aliquip ex ea commodo consequat Duis aute irure dolor in"
					+ " reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.").toUpperCase();
}
