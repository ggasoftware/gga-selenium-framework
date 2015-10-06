package com.epam.jdi_tests.tests.common;

import static com.epam.jdi_tests.enums.Preconditions.CONTACT_PAGE;
import static com.epam.jdi_tests.enums.Preconditions.HOME_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.contactFormPage;
import static com.epam.jdi_tests.page_objects.EpamJDISite.homePage;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkText;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.getDriver;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDIData.testName;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.jdi_tests.BaseScenario;
import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.dataproviders.AttrDP;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IImage;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class ImageTests extends InitTests {

	private static final String ALT = "ALT";
	private static final String SRC = "http://ecse00100176.epam.com/images/Logo_Epam_Color.svg";
	
	
	public ImageTests() {
		_onPage = HOME_PAGE;
	}
	
	@BeforeMethod
	public void before(final Method method) throws IOException {
		testName = method.getName();
		isInState(_onPage, method);
	}

	public IImage getImageHome() {
		return homePage.logoImage;
	}

	public IImage getImageContact() {
		return contactFormPage.logoImage;
	}
	
	@Test
	public void clickTest() throws InterruptedException {
		/*
		 * 1) Open contact page
		 * 2) Click on epam logo
		 * 3) Current page must be index.html
		 */
		CONTACT_PAGE.open();
		getImageContact().click();
		Assert.assertTrue(getDriver().getCurrentUrl().contains(_onPage._htmlPageName));
	}
	
	@Test(dataProvider = "attr", dataProviderClass = AttrDP.class)
	public void setAttrTest(final String attrName, final String attrValue) throws Exception {
		BaseScenario.baseSetAttrTest(this, attrName, attrValue);
	}
	
	@Test
	public void getSourceTest(){
		checkText(getImageHome()::getSource, SRC);
	}
	
	@Test
	public void getTipTest(){
		checkText(getImageHome()::getAlt, ALT);
	}
}
