package com.epam.jdi_tests.tests.common;

import com.epam.jdi_tests.InitTests;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IButton;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jdi_tests.enums.Preconditions.METALS_AND_COLORS_PAGE;
import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.*;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.*;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.isTrue;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class LabelTests extends InitTests {
	public IButton label() { return metalsColorsPage.calculateButton; }
	public static final String LABEL_NAME = "CALCULATE";

	@BeforeMethod
	public void before(final Method method) throws IOException {
		isInState(METALS_AND_COLORS_PAGE, method);
	}

	@Test
	public void clickTest() {
		label().click();
		checkCalculate("Summary: 3");
	}

	@Test
	public void getTextTest() {
		checkText(() -> label().getText(), LABEL_NAME);
	}
	@Test
	public void getValueTest() {
		checkText(() -> label().getValue(), LABEL_NAME);
	}
	@Test
	public void waitTextTest() {
		checkText(() -> label().waitText("CULATE"), LABEL_NAME);
	}

	//TODO ! contains

	@Test
	public void matchTextTest() {
		checkText(() -> label().waitMatchText("C.*C.LATE"), LABEL_NAME);
	}
	//TODO ! match
	@Test
	public void wait3TextTest() {
		isInState(SUPPORT_PAGE);
		runParallel(homePage::open);
		checkText(() -> label().waitText("CULATE"), LABEL_NAME);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}

	@Test
	public void match3TextTest() {
		checkText(() -> label().waitMatchText("C.*C.LATE"), LABEL_NAME);
	}
}
