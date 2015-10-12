package com.epam.jdi_tests.tests.common.utils;

import org.testng.annotations.Test;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkText;

import java.lang.reflect.Method;
import org.testng.annotations.BeforeMethod;

import com.epam.jdi_tests.enums.Preconditions;

public class AttributeTests {
	private String _value;
	private Preconditions _onPage;
	private Elementable _e;
	private String _attributeName;

	public AttributeTests(String attributeName, String value, Preconditions onPage, Elementable e) {
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
	@Test
	public void getAttributeTest() throws Exception {
		
	}
}
