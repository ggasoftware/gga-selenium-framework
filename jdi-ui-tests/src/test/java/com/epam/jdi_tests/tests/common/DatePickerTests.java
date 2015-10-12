package com.epam.jdi_tests.tests.common;

import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.page_objects.EpamJDISite.*;

import java.io.IOException;
import java.lang.reflect.Method;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.enums.Preconditions;
import com.epam.jdi_tests.tests.common.utils.AttributeTests;
import com.epam.jdi_tests.tests.common.utils.ContainsTextTests;
import com.epam.jdi_tests.tests.common.utils.Elementable;
import com.epam.jdi_tests.tests.common.utils.InputTests;
import com.epam.jdi_tests.tests.common.utils.Inputable;
import com.epam.jdi_tests.tests.common.utils.MatchTextTests;
import com.epam.jdi_tests.tests.common.utils.SimpleTextTests;
import com.epam.jdi_tests.tests.common.utils.Texstable;
import com.ggasoftware.jdi_ui_tests.core.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IDatePicker;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.ITextArea;

public class DatePickerTests extends InitTests {

	private static final String TEXT = Timer.nowTime("MM/dd/yyyy");

	public DatePickerTests() {
		_onPage = Preconditions.DATES_PAGE;
	}

	private IDatePicker getElement() {
		return dates._datepicker;
	}

	private Texstable getTextable() {
		return () -> { return getElement(); };
	}

	private Inputable getInputable() {
		return () -> { return getElement();	};
	}

	private Elementable gete() {
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
		return new Object[] { new SimpleTextTests(TEXT, _onPage, getTextable()),
				new MatchTextTests(TEXT, "([0-9]{2}[\\/]{1}){2}[0-9]{4}", _onPage, getTextable()),
				new ContainsTextTests(TEXT, Timer.nowTime("yyyy"), _onPage, getTextable()),
				new AttributeTests("testAttribute", "testValue", _onPage, gete()),
				new InputTests(Timer.nowTime("MM/dd/yyyy"), _onPage, getInputable()),
				};
	}
}
