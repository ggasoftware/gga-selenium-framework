package com.epam.jdi_tests.tests.commonFactory;

import com.epam.jdi_tests.InitTests;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IImage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.epam.jdi_tests.enums.Preconditions.CONTACT_PAGE;
import static com.epam.jdi_tests.enums.Preconditions.HOME_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.*;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkText;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDIData.testName;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.getDriver;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class _ImageTests extends InitTests {

	private static final String ALT = "ALT";
	private static final String SRC = "http://ecse00100176.epam.com/images/Logo_Epam_Color.svg";

	public _ImageTests() {
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
		/*
		 * 1) Open contact page 2) Click on epam logo 3) Current page must be
		 * index.html
		 */
		CONTACT_PAGE.open();
		getImageContact().click();
		Assert.assertTrue(getDriver().getCurrentUrl().contains(_onPage._htmlPageName));
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
