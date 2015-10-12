package com.epam.jdi_tests.tests.commonFactory;

import static com.epam.jdi_tests.enums.Preconditions.HOME_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.homePage;

import org.testng.annotations.Factory;
import com.epam.jdi_tests.InitTests;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IText;

public class _TextTests extends InitTests {

	public static final String TEXT = ("Lorem ipsum dolor sit amet, consectetur adipisicing elit,"
			+ " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
			+ " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris"
			+ " nisi ut aliquip ex ea commodo consequat Duis aute irure dolor in"
			+ " reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.").toUpperCase();

	public _TextTests() {
		_onPage = HOME_PAGE;
	}

	private Elementable gete() { return () -> { return (IElement) getElement(); }; }
	private Texstable get()	   { return () -> { return getElement(); }; }

	private IText getElement() {
		return homePage.text;
	}

	@Factory
	public Object[] factory() {
		return new Object[] { 
				new SimpleTextTests(TEXT, _onPage, get()),
				new MatchTextTests(TEXT, ".* IPSUM DOLOR SIT AMET.*", _onPage, get()),
				new ContainsTextTests(TEXT, "ENIM AD MINIM VENIAM, QUIS NOSTRUD", _onPage, get()),
				new AttributeTests("testAttribute", "testValue", _onPage, gete())
				};
	}
}
