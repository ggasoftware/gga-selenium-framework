package com.epam.jdi_tests.tests.commonFactory;

import static com.epam.jdi_tests.enums.Preconditions.HOME_PAGE;
import static com.epam.jdi_tests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.footer;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.page_objects.EpamJDISite.supportPage;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkText;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.runParallel;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.waitTimeOut;
import static com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Assert.isTrue;

import java.lang.reflect.Method;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import com.epam.jdi_tests.InitTests;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.ILink;

public class _LinkTests extends InitTests {
	public final static String TEXT = "About";
	public final static String HREF = "http://ecse00100176.epam.com/page3.htm";

	public _LinkTests() {
		_onPage = HOME_PAGE;
	}

	private ILink getElement() {
		return footer.about;
	}

	private Texstable get() {
		return () -> {	return getElement(); };
	}
	
	private Elementable gete() {
		return () -> { return (IElement) getElement(); };
	}

	@BeforeMethod
	public void before(final Method method) {
		isInState(_onPage, method);
	}

	@Test
	public void textWaitTestWithButton() {
		getElement().click();
		supportPage.checkOpened();
	}

	// reference
	@Test
	public void waitReferenceTest() {
		isInState(SUPPORT_PAGE);
		runParallel(_onPage::open);
		checkText(() -> getElement().waitReference("page3.htm"), HREF);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}

	@Test
	public void waitMatchReferenceTest() throws Exception {
		isInState(SUPPORT_PAGE);
		runParallel(_onPage::open);
		checkText(() -> getElement().waitMatchReference(".*htm"), HREF);
		isTrue(timer.timePassedInMSec() > waitTimeOut);
	}
	// !reference

	@Factory
	public Object[] factory() {
		return new Object[] { new SimpleTextTests(TEXT, _onPage, get()),
				new MatchTextTests(TEXT, "Abou.*", _onPage, get()),
				new ContainsTextTests(TEXT, "About", _onPage, get()),
				new AttributeTests("testAttribute", "testValue", _onPage, gete()) };
	}
}
