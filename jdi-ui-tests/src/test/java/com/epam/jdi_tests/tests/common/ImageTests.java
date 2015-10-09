package com.epam.jdi_tests.tests.common;

import com.epam.jdi_tests.InitTests;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IImage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jdi_tests.enums.Preconditions.HOME_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.*;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.areEquals;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class ImageTests extends InitTests {
	public IImage image() { return homePage.logoImage; }

	@BeforeMethod
	public void before(final Method method) throws IOException {
		isInState(HOME_PAGE, method);
	}

	@Test
	public void clickTest() {
		contactFormPage.open();
		image().click();
		homePage.checkOpened();
	}
	
	@Test
	public void getSourceTest(){
		areEquals(image().getSource(), "http://ecse00100176.epam.com/images/Logo_Epam_Color.svg");
	}

	@Test
	public void getTipTest(){
		areEquals(image().getAlt(), "ALT");
	}
}
