package com.epam.jditests.tests.common;

import com.epam.jditests.InitTests;
import com.epam.jditests.enums.Preconditions;
import com.epam.jditests.tests.common.utils.AttributeTests;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IImage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jditests.enums.Preconditions.HOME_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.*;
import static com.epam.jditests.tests.complex.CommonActionsData.checkText;
import static com.ggasoftware.jdiuitests.core.settings.JDIData.testName;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class ImageTests extends InitTests {

	private static final String ALT = "ALT";
	private static final String SRC = "http://ecse00100176.epam.com/images/Logo_Epam_Color.svg";
	private Preconditions _onPage = null;
	
	public ImageTests() {
		_onPage = HOME_PAGE;
	}

	public IImage getImageHome() {
		return homePage.logoImage;
	}

	public IImage getImageContact() {
		return contactFormPage.logoImage;
	}

	@BeforeMethod
	public void before(final Method method) throws IOException {
		testName = method.getName();
		isInState(_onPage, method);
	}

	@Test
	public void clickTest() throws InterruptedException {
		contactFormPage.open();
		getImageContact().click();
		homePage.checkOpened();
	}

	@Factory
	public Object[] factory() {
		return new Object[] { new AttributeTests("testAttribute", "testValue", _onPage, () -> {
			return (IElement) getImageHome();
		}) };
	}

	@Test
	public void getSourceTest() {
		checkText(getImageHome()::getSource, SRC);
	}

	@Test
	public void getTipTest() {
		checkText(getImageHome()::getAlt, ALT);
	}
}
