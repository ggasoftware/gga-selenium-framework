package com.epam.jditests.tests.common.utils;

import com.epam.jditests.enums.Preconditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.tests.complex.CommonActionsData.checkText;

public class SimpleTextTests {

	private String _expected;
	private Preconditions _onPage;
	private ITexstable _t;
	
	public SimpleTextTests(String expected, Preconditions onPage, ITexstable t) {
		_expected = expected;
		_onPage = onPage;
		this._t = t;
	}
	
	@BeforeMethod
	public void before(final Method method) {
		isInState(_onPage, method);
	}

	@Test
	public void getTextTest() throws Exception {
		checkText(_t.getTextElement()::getText, _expected);
	}

	@Test
	public void getValueTest() throws Exception {
		checkText(_t.getTextElement()::getValue, _expected);
	}
}
