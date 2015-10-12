package com.epam.jdi_tests.tests.common;

import static com.epam.jdi_tests.enums.Preconditions.HOME_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.homePage;

import org.testng.annotations.Factory;
import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.enums.Preconditions;
import com.epam.jdi_tests.tests.common.utils.AttributeTests;
import com.epam.jdi_tests.tests.common.utils.ContainsTextTests;
import com.epam.jdi_tests.tests.common.utils.IElementable;
import com.epam.jdi_tests.tests.common.utils.MatchTextTests;
import com.epam.jdi_tests.tests.common.utils.SimpleTextTests;
import com.epam.jdi_tests.tests.common.utils.ITexstable;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IText;

public class TextTests extends InitTests {
	private Preconditions _onPage = null;
	
	public static final String TEXT = ("Lorem ipsum dolor sit amet, consectetur adipisicing elit,"
			+ " sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
			+ " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris"
			+ " nisi ut aliquip ex ea commodo consequat Duis aute irure dolor in"
			+ " reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.").toUpperCase();

	public TextTests() {
		_onPage = HOME_PAGE;
	}

	private IElementable gete() { return () -> { return (IElement) getElement(); }; }
	private ITexstable get()	   { return () -> { return getElement(); }; }

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
