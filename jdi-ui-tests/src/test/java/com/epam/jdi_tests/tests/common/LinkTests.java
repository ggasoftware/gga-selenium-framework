package com.epam.jdi_tests.tests.common;

import static com.epam.jdi_tests.BaseScenario.baseGetTextTest;
import static com.epam.jdi_tests.BaseScenario.baseGetValueTest;
import static com.epam.jdi_tests.BaseScenario.baseWait3TextMatchTest;
import static com.epam.jdi_tests.BaseScenario.baseWait3TextTest;
import static com.epam.jdi_tests.BaseScenario.baseWaitTextMatchTest;
import static com.epam.jdi_tests.BaseScenario.baseWaitTextTest;
import static com.epam.jdi_tests.dataproviders.LinkDP.TEXT;
import static com.epam.jdi_tests.enums.Preconditions.HOME_PAGE;
import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.footer;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.page_objects.EpamJDISite.supportPage;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkText;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.runParallel;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.waitTimeOut;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.isTrue;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.jdi_tests.BaseScenario;
import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.dataproviders.AttrDP;
import com.epam.jdi_tests.dataproviders.LinkDP;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.ILink;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class LinkTests extends InitTests {

	public LinkTests() {
		_onPage = HOME_PAGE;
	}

	@Override
	public ILink textElement() {
		return footer.about;
	}

	@BeforeMethod
	public void before(final Method method) throws IOException {
		isInState(_onPage, method);
	}

	@Test
	public void textWaitTestWithButton() {
		textElement().click();
		supportPage.checkOpened();
	}

	// WAIT
	@Test(dataProvider = "waitText", dataProviderClass = LinkDP.class)
	public void waitTextTest(final String contains, final String expected) {
		baseWaitTextTest(this, contains, expected);
	}

	@Test(dataProvider = "waitText", dataProviderClass = LinkDP.class)
	public void wait3TextTest(final String contains, final String expected) {
		baseWait3TextTest(this, contains, expected);
	}
	// !WAIT

	// MATCH
	@Test(dataProvider = "matchText", dataProviderClass = LinkDP.class)
	public void waitTextMatchTest(final String regex, final String expected) {
		baseWaitTextMatchTest(this, regex, expected);
	}

	@Test(dataProvider = "matchText", dataProviderClass = LinkDP.class)
	public void wait3TextMatchTest(final String regex, final String expected) {
		baseWait3TextMatchTest(this, regex, expected);
	}
	// !MATCH

	// reference
	@Test(dataProvider = "waitRef", dataProviderClass = LinkDP.class)
	public void waitReferenceTest(final String contains, final String expected) throws Exception {
		isInState(SUPPORT_PAGE);
		runParallel(_onPage::open);
		checkText(() -> textElement().waitReference(contains), expected);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}
	
	@Test(dataProvider = "matchRef", dataProviderClass = LinkDP.class)
	public void waitMatchReferenceTest(final String regex, final String expected) throws Exception {
		isInState(SUPPORT_PAGE);
		runParallel(_onPage::open);
		checkText(() -> textElement().waitMatchReference(regex), expected);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}
	// !reference
	
	@Test(priority = 1)
	public void getTextTest() {
		baseGetTextTest(this, TEXT);
	}

	@Test(priority = 1)
	public void getValueTest() {
		baseGetValueTest(this, TEXT);
	}

	@Test(dataProvider = "attr", dataProviderClass = AttrDP.class)
	public void setAttrTest(final String attrName, final String attrValue) throws Exception {
		BaseScenario.baseSetAttrTest(this, attrName, attrValue);
	}

	@Test
	public void getTooltipTest() throws Exception {
		checkText(() -> textElement().getTooltip(), textElement().getWebElement().getAttribute("title"));
	}
}
