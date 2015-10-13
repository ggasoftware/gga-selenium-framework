package com.epam.jditests.tests.common;

import com.epam.jditests.InitTests;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ILink;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jditests.enums.Preconditions.HOME_PAGE;
import static com.epam.jditests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.*;
import static com.epam.jditests.tests.complex.CommonActionsData.*;
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
		checkText(() -> link().getText(), LINK_NAME);
	}
	@Test
	public void getValueTest() {
		checkText(() -> link().getValue(), LINK_NAME);
	}
	@Test
	public void waitTextTest() {
		checkText(() -> link().waitText("CULATE"), LINK_NAME);
	}

	//TODO ! contains

	@Test
	public void matchTextTest() {
		checkText(() -> link().waitMatchText("C.*C.LATE"), LINK_NAME);
	}
	//TODO ! match
	@Test
	public void wait3TextTest() {
		isInState(SUPPORT_PAGE);
		runParallel(homePage::open);
		checkText(() -> link().waitText("CULATE"), LINK_NAME);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}

	@Test
	public void match3TextTest() {
		checkText(() -> link().waitMatchText("C.*C.LATE"), LINK_NAME);
	}
	@Test
	public void getTooltipTest() throws Exception {
		checkText(() -> link().getTooltip(), "Tip title");
	}
}
