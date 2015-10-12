package com.epam.jdi_tests.tests.commonFactory;

import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkText;

import java.lang.reflect.Method;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.epam.jdi_tests.enums.Preconditions;

public class SimpleTextTests {

	private String _expected;
	private Preconditions _onPage;
	private Texstable _t;
	
	public SimpleTextTests(String expected, Preconditions onPage, Texstable t) {
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
