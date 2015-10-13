package com.epam.jditests.tests.common;

import com.epam.jditests.InitTests;
import com.epam.jditests.dataproviders.TextDP;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IText;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jditests.enums.Preconditions.HOME_PAGE;
import static com.epam.jditests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.homePage;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.tests.complex.CommonActionsData.*;
import static com.epam.jditests.tests.complex.CommonActionsData.checkText;
import static com.epam.jditests.tests.complex.CommonActionsData.waitTimeOut;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.isTrue;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class TextTests extends InitTests {

	public IText text() {
		return homePage.text;
	}

	@BeforeMethod
	public void before(final Method method) throws IOException {
		isInState(HOME_PAGE, method);
	}
	@Test
	public void getTextTest() {
		checkText(() -> text().getText(), TEXT);
	}
	@Test
	public void getValueTest() {
		checkText(() -> text().getValue(), TEXT);
	}
	@Test(dataProvider = "waitText", dataProviderClass = TextDP.class)
	public void waitTextTest(String contains) {
		checkText(() -> text().waitText(contains), TEXT);
	}

	//TODO ! contains

	@Test
	public void matchTextTest() {
		checkText(() -> text().waitMatchText(".* ipsum dolor sit amet.*".toUpperCase()), TEXT);
	}
	//TODO ! match
	@Test
	public void wait3TextTest() {
		isInState(SUPPORT_PAGE);
		runParallel(homePage::open);
		checkText(() -> text().waitText("Lorem ipsum dolor sit amet".toUpperCase()), TEXT);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}

	@Test
	public void match3TextTest() {
		checkText(() -> text().waitMatchText(".* ipsum dolor sit amet.*".toUpperCase()), TEXT);
	}

	public static final String TEXT =
			("Lorem ipsum dolor sit amet, consectetur adipisicing elit,"
					+ " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
					+ " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris"
					+ " nisi ut aliquip ex ea commodo consequat Duis aute irure dolor in"
					+ " reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.").toUpperCase();
}
