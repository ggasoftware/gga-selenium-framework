package com.epam.jdi_tests.tests.common;

import static com.epam.jdi_tests.enums.Preconditions.METALS_AND_COLORS_PAGE;
import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.page_objects.EpamJDISite.metalsColorsPage;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.checkCalculate;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.tests.common.utils.AttributeTests;
import com.epam.jdi_tests.tests.common.utils.ContainsTextTests;
import com.epam.jdi_tests.tests.common.utils.Elementable;
import com.epam.jdi_tests.tests.common.utils.MatchTextTests;
import com.epam.jdi_tests.tests.common.utils.SimpleTextTests;
import com.epam.jdi_tests.tests.common.utils.Texstable;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.ILabel;

@Test(testName = "Label")
public class LabelTests extends InitTests{
	
	public static final String TEXT = "CALCULATE";
	
	public LabelTests() {
		_onPage = METALS_AND_COLORS_PAGE;
	}
	
	private ILabel getElement() {
		return metalsColorsPage.calculate;
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
		checkCalculate("Summary: 3");
	}
	
	@Factory
	public Object[] factory() {
		return new Object[] { 
				new SimpleTextTests(TEXT, _onPage, get()),
				new MatchTextTests(TEXT, ".*LCU.*", _onPage, get()),
				new ContainsTextTests(TEXT, "CUL", _onPage, get()),
				new AttributeTests("testAttribute", "testValue", _onPage, gete())
				};
	}
}
