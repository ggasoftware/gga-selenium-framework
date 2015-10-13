package com.epam.jditests.tests.common;

import static com.epam.jditests.enums.Preconditions.HOME_PAGE;
import static com.epam.jditests.enums.Preconditions.SUPPORT_PAGE;
import static com.epam.jditests.pageobjects.EpamJDISite.footer;
import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.pageobjects.EpamJDISite.supportPage;
import static com.epam.jditests.tests.complex.CommonActionsData.checkText;
import static com.epam.jditests.tests.complex.CommonActionsData.runParallel;
import static com.epam.jditests.tests.complex.CommonActionsData.waitTimeOut;
import static com.ggasoftware.jdiuitests.implementation.testng.asserter.Assert.isTrue;

import java.lang.reflect.Method;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import com.epam.jditests.InitTests;
import com.epam.jditests.enums.Preconditions;
import com.epam.jditests.tests.common.utils.AttributeTests;
import com.epam.jditests.tests.common.utils.ContainsTextTests;
import com.epam.jditests.tests.common.utils.IElementable;
import com.epam.jditests.tests.common.utils.MatchTextTests;
import com.epam.jditests.tests.common.utils.SimpleTextTests;
import com.epam.jditests.tests.common.utils.ITexstable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ILink;

public class LinkTests extends InitTests {
	public final static String TEXT = "About";
	public final static String HREF = "http://ecse00100176.epam.com/page3.htm";
	private Preconditions _onPage = null;
	
	public LinkTests() {
		_onPage = HOME_PAGE;
	}

	private ILink getElement() {
		return footer.about;
	}

	private ITexstable get() {
		return () -> {	return getElement(); };
	}
	
	private IElementable gete() {
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