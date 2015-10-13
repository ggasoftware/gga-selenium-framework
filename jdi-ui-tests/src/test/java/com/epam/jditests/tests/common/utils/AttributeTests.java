package com.epam.jditests.tests.common.utils;

import com.epam.jditests.enums.Preconditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.tests.complex.CommonActionsData.checkText;

public class AttributeTests {
	private String _value;
	private Preconditions _onPage;
	private IElementable _e;
	private String _attributeName;

	public AttributeTests(String attributeName, String value, Preconditions onPage, IElementable e) {
		this._value = value;
		this._attributeName = attributeName;
		this._onPage = onPage;
		this._e = e;
	}
	
	@BeforeMethod
	public void before(final Method method) {
		isInState(_onPage, method);
	}
	
	@Test
	public void setAttributeTest() throws Exception {
		_e.getElement().setAttribute(_attributeName, _value);
		checkText(() -> _e.getElement().getWebElement().getAttribute(_attributeName), _value);
	}
	
	// TODO
//	@Test
//	public void getAttributeTest() throws Exception {
//		
//	}
}
