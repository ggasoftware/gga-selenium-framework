package com.epam.jditests.tests.common;

import static com.epam.jditests.pageobjects.EpamJDISite.isInState;
import static com.epam.jditests.pageobjects.EpamJDISite.*;
import static com.epam.jditests.CommonData.TEST_DATE;
import java.io.IOException;
import java.lang.reflect.Method;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import com.epam.jditests.InitTests;
import com.epam.jditests.enums.Preconditions;
import com.epam.jditests.tests.common.utils.AttributeTests;
import com.epam.jditests.tests.common.utils.ContainsTextTests;
import com.epam.jditests.tests.common.utils.IElementable;
import com.epam.jditests.tests.common.utils.InputTests;
import com.epam.jditests.tests.common.utils.IInputable;
import com.epam.jditests.tests.common.utils.MatchTextTests;
import com.epam.jditests.tests.common.utils.SimpleTextTests;
import com.epam.jditests.tests.common.utils.ITexstable;
import com.ggasoftware.jdiuitests.core.utils.common.Timer;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IDatePicker;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextArea;

public class DatePickerTests extends InitTests {
	private Preconditions _onPage = null;

	public DatePickerTests() {
		_onPage = Preconditions.DATES_PAGE_FILLED;
	}

	private IDatePicker getElement() {
		return dates.datepicker;
	}

	private ITexstable getTextable() {
		return () -> { return getElement(); };
	}

	private IInputable getInputable() {
		return () -> { return getElement();	};
	}

	private IElementable gete() {
		return () -> { return (IElement) getElement(); };
	}

	public ITextArea input() {
		return (ITextArea) getInputable().getInputElement();
	}

	@BeforeMethod
	public void before(final Method method) throws IOException {
		isInState(_onPage, method);
	}

	@AfterMethod
	public void after() {
		_onPage.open();
	}


	@Factory
	public Object[] factory() {
		return new Object[] { new SimpleTextTests(TEST_DATE, _onPage, getTextable()),
				new MatchTextTests(TEST_DATE, "([0-9]{2}[\\/]{1}){2}[0-9]{4}", _onPage, getTextable()),
				new ContainsTextTests(TEST_DATE, "1945", _onPage, getTextable()),
				new AttributeTests("testAttribute", "testValue", _onPage, gete()),
				new InputTests(Timer.nowTime("MM/dd/yyyy"), _onPage, getInputable()),
				};
	}
}
