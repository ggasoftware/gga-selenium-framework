package com.epam.jdi_tests.tests.commonFactory;

import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.page_objects.EpamJDISite.metalsColorsPage;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkAction;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDIData.testName;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.enums.Preconditions;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.ICheckBox;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */

public class _CheckboxTests extends InitTests {

    public _CheckboxTests() {
    	_onPage = Preconditions.METALS_AND_COLORS_PAGE;
	}
    
    public ICheckBox getCheckBox() {
		return metalsColorsPage.cbWater;
	}
    
    @BeforeMethod
    public void before(final Method method) throws IOException {
    	testName = method.getName();
    	isInState(_onPage, method);
    }
    
    @Test
    public void checkSingleTest() throws InterruptedException {
    	// At the start checkbox is unchecked
    	// 1. Check checkbox by JDI method
    	getCheckBox().check();
    	// 2. See log
    	checkAction("Water: condition changed to true");
    	// 3. See element by purely Selenium method
    	// TODO 
    	// Assert.assertTrue(getCheckBox().getWebElement().isSelected());
    }
    
    @Test
    public void uncheckSingleTest() throws InterruptedException {
    	// At the start checkbox is unchecked
    	// 1. Checked checkbox by purely Selenium method 
    	getCheckBox().getWebElement().click();
    	// 2. Unchecked checkbox by JDI method 
    	getCheckBox().uncheck();
    	// 2. See log
    	checkAction("Water: condition changed to false");
    	// TODO
    	// 3. See element by purely Selenium method
    	// Assert.assertFalse(getCheckBox().getWebElement().isSelected());
    }
    
    @Test
	public void isCheckTest() throws Exception {
		Assert.assertFalse(getCheckBox().isChecked());
		getCheckBox().getWebElement().click();
		Assert.assertTrue(getCheckBox().isChecked());
	}
    
    @Test
	public void multiUncheckTest() throws Exception {
    	getCheckBox().getWebElement().click();
    	getCheckBox().uncheck();
    	getCheckBox().uncheck();
    	checkAction("Water: condition changed to false");
	}
    
    @Test
	public void multiCheckTest() throws Exception {
    	getCheckBox().check();
    	getCheckBox().check();
    	checkAction("Water: condition changed to true");
	}
    
    @Test
	public void clickTest() throws Exception {
		getCheckBox().click();
		checkAction("Water: condition changed to true");
		getCheckBox().click();
		checkAction("Water: condition changed to false");
	}
    
}
