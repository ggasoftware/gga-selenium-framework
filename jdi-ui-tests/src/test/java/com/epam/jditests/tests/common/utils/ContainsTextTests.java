package com.epam.jditests.tests.common.utils;

import com.epam.jditests.enums.Preconditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jditests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.tests.complex.CommonActionsData.*;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.isTrue;

public class ContainsTextTests {
	private String _expected;
	private Preconditions _onPage;
	private ITexstable _t;
	private String _contains;

	public ContainsTextTests(String expected, String contains, Preconditions onPage, ITexstable t) {
		super();
		this._expected = expected;
		this._contains = contains;
		this._onPage = onPage;
		this._t = t;
	}

	@BeforeMethod
	public void before(final Method method) {
		isInState(_onPage, method);
	}

	@Test
	public void waitText() throws Exception {
		checkText(() -> _t.getTextElement().waitText(_contains), _expected);
	}
	
	@Test
	public void waitTextParallel() throws Exception {
		isInState(SUPPORT_PAGE);
		runParallel(_onPage::open);
		checkText(() -> _t.getTextElement().waitText(_contains), _expected);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}
}
