package com.epam.jdi_tests.tests.common;

import com.epam.jdi_tests.InitTests;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ILink;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jdi_tests.enums.Preconditions.HOME_PAGE;
import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.*;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.*;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.areEquals;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.isTrue;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class LinkTests extends InitTests {
	public ILink link() { return footer.about; }
	public static final String LINK_NAME = "About";

	@BeforeMethod
	public void before(final Method method) throws IOException {
		isInState(HOME_PAGE, method);
	}

	@Test
	public void clickTest() {
		link().click();
		supportPage.checkOpened();
	}

	@Test
	public void getTextTest() {
		areEquals(link().getText(), LINK_NAME);
	}
	@Test
	public void getValueTest() {
		areEquals(link().getValue(), LINK_NAME);
	}
	@Test
	public void waitTextTest() {
		areEquals(link().waitText("bout"), LINK_NAME);
	}

	//TODO ! contains

	@Test
	public void matchTextTest() {
		areEquals(link().waitMatchText(".*ut"), LINK_NAME);
	}
	//TODO ! match
	@Test
	public void wait3TextTest() {
		isInState(SUPPORT_PAGE);
		runParallel(homePage::open);
		areEquals(link().waitText("bout"), LINK_NAME);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}

	@Test
	public void match3TextTest() {
		isInState(SUPPORT_PAGE);
		runParallel(homePage::open);
		areEquals(link().waitMatchText(".*ut"), LINK_NAME);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}
	@Test
	public void getTooltipTest() throws Exception {
		areEquals(link().getTooltip(), "Tip title");
	}
}
