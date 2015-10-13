package com.epam.jditests.tests.common;

import com.epam.jditests.InitTests;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IButton;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jditests.enums.Preconditions.METALS_AND_COLORS_PAGE;
import static com.epam.jditests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jditests.page_objects.EpamJDISite.isInState;
import static com.epam.jditests.page_objects.EpamJDISite.metalsColorsPage;
import static com.epam.jditests.tests.complex.CommonActionsData.*;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.areEquals;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.isTrue;

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
		areEquals(button().getText(), BUTTON_NAME);
	}
	@Test
	public void getValueTest() {
		areEquals(button().getValue(), BUTTON_NAME);
	}
	@Test
	public void waitTextTest() {
		areEquals(button().waitText("CULATE"), BUTTON_NAME);
	}

	//TODO ! contains

	@Test
	public void matchTextTest() {
		areEquals(button().waitMatchText("C.*C.LATE"), BUTTON_NAME);
	}
	//TODO ! match
	@Test
	public void wait3TextTest() {
		isInState(SUPPORT_PAGE);
		runParallel(metalsColorsPage::open);
		areEquals(button().waitText("CULATE"), BUTTON_NAME);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}

	@Test
	public void match3TextTest() {
		isInState(SUPPORT_PAGE);
		runParallel(metalsColorsPage::open);
		areEquals(button().waitMatchText("C.*C.LATE"), BUTTON_NAME);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}
}
