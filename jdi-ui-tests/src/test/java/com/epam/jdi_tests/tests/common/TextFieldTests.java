package com.epam.jdi_tests.tests.common;

import static com.epam.jdi_tests.entities.User.DEFAULT_USER;
import static com.epam.jdi_tests.enums.Preconditions.CONTACT_PAGE_WITH_FILLED_FIELDS;
import static com.epam.jdi_tests.page_objects.EpamJDISite.contactFormPage;

import org.testng.annotations.Factory;
import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.enums.Preconditions;
import com.epam.jdi_tests.tests.common.utils.AttributeTests;
import com.epam.jdi_tests.tests.common.utils.ContainsTextTests;
import com.epam.jdi_tests.tests.common.utils.IElementable;
import com.epam.jdi_tests.tests.common.utils.InputTests;
import com.epam.jdi_tests.tests.common.utils.IInputable;
import com.epam.jdi_tests.tests.common.utils.MatchTextTests;
import com.epam.jdi_tests.tests.common.utils.SimpleTextTests;
import com.epam.jdi_tests.tests.common.utils.ITexstable;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.ITextField;

public class TextFieldTests extends InitTests {

	public static final String TEXT = DEFAULT_USER.name;
	private Preconditions _onPage = null;
	
	public TextFieldTests() {
		_onPage = CONTACT_PAGE_WITH_FILLED_FIELDS;
	}

	private ITexstable getTextable()  { return () -> { return getElement(); }; }
	private IElementable gete() 	 	 { return () -> { return (IElement) getElement(); }; }
	private IInputable getInputable() { return () -> { return getElement(); }; }

	private ITextField getElement() {
		return contactFormPage.name;
	}
	
	@Factory
	public Object[] factory() {
		return new Object[] { 
				new SimpleTextTests(TEXT, _onPage, getTextable()),
				new MatchTextTests(TEXT, ".am.", _onPage, getTextable()),
				new ContainsTextTests(TEXT, "ame", _onPage, getTextable()),
				new AttributeTests("testAttribute", "testValue", _onPage, gete()),
				new InputTests("inputText", _onPage, getInputable()),
				new InputTests("1234567890", _onPage, getInputable()),
				new InputTests("!@#^&*()_", _onPage, getInputable())
				};
	}
}
