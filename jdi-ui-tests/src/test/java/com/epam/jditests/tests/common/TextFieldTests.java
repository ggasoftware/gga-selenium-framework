package com.epam.jditests.tests.common;

import com.epam.jditests.InitTests;
import com.epam.jditests.enums.Preconditions;
import com.epam.jditests.tests.common.utils.*;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.ITextField;
import org.testng.annotations.Factory;

import static com.epam.jditests.entities.User.DEFAULT_USER;
import static com.epam.jditests.enums.Preconditions.CONTACT_PAGE_FILLED;
import static com.epam.jditests.pageobjects.EpamJDISite.contactFormPage;

public class TextFieldTests extends InitTests {

	public static final String TEXT = DEFAULT_USER.name;
	private Preconditions _onPage = null;
	
	public TextFieldTests() {
		_onPage = CONTACT_PAGE_FILLED;
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
