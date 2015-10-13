package com.epam.jditests.tests.common;

import com.epam.jditests.InitTests;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IImage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jditests.enums.Preconditions.HOME_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.*;
import static com.epam.jditests.tests.complex.CommonActionsData.checkText;

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
		checkText(image()::getSource, "http://ecse00100176.epam.com/images/Logo_Epam_Color.svg");
	}

	@Test
	public void getTipTest(){
		checkText(image()::getAlt, "ALT");
	}
}
