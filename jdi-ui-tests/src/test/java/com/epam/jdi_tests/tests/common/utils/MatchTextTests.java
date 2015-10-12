package com.epam.jdi_tests.tests.common.utils;

import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkText;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.runParallel;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.timer;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.waitTimeOut;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.isTrue;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.jdi_tests.enums.Preconditions;

public class MatchTextTests {
	private String _expected;
	private String _regex;
	private Preconditions _onPage;
	private ITexstable _t;

	public MatchTextTests(String expected, String regex, Preconditions onPage, ITexstable t) {
		super();
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
