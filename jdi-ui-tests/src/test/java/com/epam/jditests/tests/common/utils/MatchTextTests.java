package com.epam.jditests.tests.common.utils;

import com.epam.jditests.enums.Preconditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jditests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.tests.complex.CommonActionsData.*;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.isTrue;

public class MatchTextTests {
	private String _expected;
	private String _regex;
	private Preconditions _onPage;
	private ITexstable _t;

	public MatchTextTests(String expected, String regex, Preconditions onPage, ITexstable t) {
		this._expected = expected;
		this._regex = regex;
		this._onPage = onPage;
		this._t = t;
	}

	@BeforeMethod
	public void before(final Method method) {
		isInState(_onPage, method);
	}

	@Test
	public void waitMatchText() throws Exception {
		checkText(() -> _t.getTextElement().waitMatchText(_regex), _expected);
	}
	
	@Test
	public void waitMatchTextParallel() throws Exception {
		isInState(SUPPORT_PAGE);
		runParallel(_onPage::open);
		checkText(() -> _t.getTextElement().waitMatchText(_regex), _expected);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}
	
}
