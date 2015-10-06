package com.epam.jdi_tests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.Assert;

import com.ggasoftware.jdi_ui_tests.core.settings.JDISettings;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.ITextField;

public class BaseScenarioInput {

	public static void inputTest(final InitTests test, final String current, final String expected) throws Exception {
		final ITextField t = (ITextField) test.textElement();
		getJSExec().executeScript("arguments[0].value=\"\"", t.getWebElement());
		t.input(current);
		t.getWebElement().sendKeys(Keys.ESCAPE);
		final String resText = getJSExec().executeScript("return arguments[0].value", t.getWebElement())
				.toString();
		Assert.assertEquals(resText, expected);
	}

	public static void sendKeysTest(final InitTests test, final String current, final String expected)
			throws Exception {
		final ITextField t = (ITextField) test.textElement();
		getJSExec().executeScript("arguments[0].value=\"\"", t.getWebElement());
		t.input(current);
		final String resText = getJSExec().executeScript("return arguments[0].value", t.getWebElement())
				.toString();
		Assert.assertEquals(resText, expected);
	}

	public static void newInputTest(final InitTests test, final String in) throws Exception {
		final ITextField t = (ITextField) test.textElement();
		getJSExec().executeScript("arguments[0].value=\"garbageString\"", t.getWebElement());
		t.newInput(in);
		final String resText = getJSExec().executeScript("return arguments[0].value", t.getWebElement())
				.toString();
		Assert.assertEquals(in, resText);
	}

	public static void clearTest(final InitTests test) throws Exception {
		final ITextField t = (ITextField) test.textElement();
		getJSExec().executeScript("arguments[0].value=\"garbageString\"", t.getWebElement());
		t.clear();
		final String resText = getJSExec().executeScript("return arguments[0].value", t.getWebElement())
				.toString();
		Assert.assertTrue(resText.length() == 0);
	}
	
	public static void multiKeyTest(final InitTests test, final String inputText) throws Exception{
		final ITextField t = (ITextField) test.textElement();
		getJSExec().executeScript("arguments[0].value=\"\"", t.getWebElement());
		for (int i = 0; i < inputText.length(); i++) {
			t.sendKeys(Character.toString(inputText.charAt(i)));
		}
		final String resText = getJSExec().executeScript("return arguments[0].value", t.getWebElement())
				.toString();
		Assert.assertEquals(inputText, resText);
	}

	public static void focusTest(final InitTests test, final String id) throws Exception{
		/*
		 * 1. Set new attribute to tested element
		 * 2. Set focus on element by JDI method
		 * 3. Get attribute value on focused element by WebDriver method 
		 * */
		final ITextField t = (ITextField) test.textElement();
		getJSExec().executeScript("arguments[0].setAttribute(\"test\",\"" + id + "\");", t.getWebElement());
		t.focus();
		final String res = JDISettings.getDriver().switchTo().activeElement().getAttribute("test");
		Assert.assertEquals(id, res);
	}
	
	public static JavascriptExecutor getJSExec() {
		return ((JavascriptExecutor)JDISettings.getDriver());
	}

}
