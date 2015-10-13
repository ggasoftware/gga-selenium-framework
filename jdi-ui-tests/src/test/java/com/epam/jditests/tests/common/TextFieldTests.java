package com.epam.jditests.tests.common;

import com.epam.jditests.InitTests;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class TextFieldTests extends InitTests {

/*
TODO
	public TextFieldTests() {
		_onPage = CONTACT_PAGE_WITH_FILLED_FIELDS;
	}

	@Override
	public ITextField textElement() {
		return contactFormPage.name;
	}

	@BeforeMethod
	public void before(final Method method) throws IOException {
		isInState(_onPage, method);
	}

	// WAIT
	@Test(dataProvider = "waitText", dataProviderClass = TextFieldDP.class)
	public void waitTextTest(final String contains, final String expected) {
		baseWaitTextTest(this, contains, expected);
	}

	@Test(dataProvider = "waitText", dataProviderClass = TextFieldDP.class)
	public void wait3TextTest(final String contains, final String expected) {
		baseWait3TextTest(this, contains, expected);
	}
	// !WAIT

	// MATCH
	@Test(dataProvider = "matchText", dataProviderClass = TextFieldDP.class)
	public void waitTextMatchTest(final String regex, final String expected) {
		baseWaitTextMatchTest(this, regex, expected);
	}

	@Test(dataProvider = "matchText", dataProviderClass = TextFieldDP.class)
	public void wait3TextMatchTest(final String regex, final String expected) {
		baseWait3TextMatchTest(this, regex, expected);
	}
	// !MATCH

	// INPUT
	@Test(dataProvider = "inputText", dataProviderClass = TextFieldDP.class)
	public void inputTest(final String in, final String expected) throws Exception {
		BaseScenarioInput.inputTest(this, in, expected);
	}
	
	@Test(dataProvider = "inputText", dataProviderClass = TextFieldDP.class)
	public void sendKeysTest(final String in, final String expected) throws Exception {
		BaseScenarioInput.sendKeysTest(this, in, expected);
	}
	
	@Test
	public void newInputTest() throws Exception {
		BaseScenarioInput.newInputTest(this, "TestString123!@$");
	}
	
	@Test
	public void clearTest() throws Exception {
		BaseScenarioInput.clearTest(this);
	}
	
	@Test
	public void multiKeyTest() throws Exception {
		BaseScenarioInput.multiKeyTest(this, "TestString123@#^");
	}
	// !INPUT
	
	@Test
	public void focusTest() throws Exception {
		BaseScenarioInput.focusTest(this, "test123");
	}
	
	@Test
	public void getTextTest() {
		_onPage.open();
		baseGetTextTest(this, DEFAULT_USER.name);
	}

	@Test
	public void getValueTest() {
		_onPage.open();
		checkText(textElement()::getValue, DEFAULT_USER.name);
	}
	*/
}
