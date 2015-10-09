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

public class ButtonTest extends InitTests {
	public IButton button() { return metalsColorsPage.calculateButton; }
	public static final String BUTTON_NAME = "CALCULATE";

	@BeforeMethod
	public void before(Method method) throws IOException {
		isInState(METALS_AND_COLORS_PAGE, method);
	}

	@Test
	public void clickTest() {
		button().click();
		checkCalculate("Summary: 3");
	}

	@Test
	public void getTextTest() {
		checkText(() -> button().getText(), BUTTON_NAME);
	}
	@Test
	public void getValueTest() {
		checkText(() -> button().getValue(), BUTTON_NAME);
	}
	@Test
	public void waitTextTest() {
		checkText(() -> button().waitText("CULATE"), BUTTON_NAME);
	}

	//TODO ! contains

	@Test
	public void matchTextTest() {
		checkText(() -> button().waitMatchText("C.*C.LATE"), BUTTON_NAME);
	}
	//TODO ! match
	@Test
	public void wait3TextTest() {
		isInState(SUPPORT_PAGE);
		runParallel(metalsColorsPage::open);
		checkText(() -> button().waitText("CULATE"), BUTTON_NAME);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}

	@Test
	public void match3TextTest() {
		isInState(SUPPORT_PAGE);
		runParallel(metalsColorsPage::open);
		checkText(() -> button().waitMatchText("C.*C.LATE"), BUTTON_NAME);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}
}
