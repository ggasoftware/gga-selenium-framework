package com.epam.jdi_tests;

import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkText;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.runParallel;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.timer;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.waitTimeOut;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.isTrue;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IText;

public class BaseScenario {
	public static void baseGetTextTest(final InitTests test, final String expectedRes) {
		try {
			checkText(test.textElement()::getText, expectedRes);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void baseWait3TextTest(final InitTests test, final String contains, final String expected) {
		try {
			final IText t = test.textElement();
			isInState(SUPPORT_PAGE);
			runParallel(test._onPage::open);
			checkText(() -> t.waitText(contains), expected);
			isTrue(timer.timePassedInMSec() > waitTimeOut);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	} 

	public static void baseWait3TextMatchTest(final InitTests test, final String regex, final String expected) {
		try {
			isInState(SUPPORT_PAGE);
			final IText t = test.textElement();
			runParallel(test._onPage::open);
			checkText(() -> t.waitMatchText(regex), expected);
			isTrue(timer.timePassedInMSec() > waitTimeOut);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void baseWaitTextMatchTest(final InitTests test, final String regex, final String expected) {
		try {
			final IText t = test.textElement();
			checkText(() -> t.waitMatchText(regex), expected);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void baseGetValueTest(final InitTests test, final String expected) {
		try {
			checkText(test.textElement()::getValue, expected);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void baseWaitTextTest(final InitTests test, final String contains, final String expected) {
		try {
			final IText t = test.textElement();
			checkText(() -> t.waitText(contains), expected);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void baseSetAttrTest(final InitTests test, final String attrName, final String attrValue){
		try {
			final IText t = test.textElement();
			t.setAttribute(attrName, attrValue);
			t.getWebElement().getAttribute(attrName);
			checkText(() -> t.getWebElement().getAttribute(attrName), attrValue);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
