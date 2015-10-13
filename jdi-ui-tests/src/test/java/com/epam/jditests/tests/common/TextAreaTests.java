package com.epam.jditests.tests.common;

import com.epam.jditests.InitTests;

/**
 * Created by Roman_Iovlev on 9/15/2015.
 */
public class TextAreaTests extends InitTests {
/*
TODO
	final String[] IN = {"line1", "line2", "line3"};
	
	@Override
	public ITextArea textElement() {
		return contactFormPage.description;
	}

	public TextAreaTests() {
		_onPage = CONTACT_PAGE_WITH_FILLED_FIELDS;
	}

	@BeforeMethod
	public void before(final Method method) throws IOException {
		isInState(_onPage, method);
	}

	// WAIT
	@Test(dataProvider = "waitText", dataProviderClass = TextAreaDP.class)
	public void waitTextTest(final String contains, final String expected) {
		baseWaitTextTest(this, contains, expected);
	}

	@Test(dataProvider = "waitText", dataProviderClass = TextAreaDP.class)
	public void wait3TextTest(final String contains, final String expected) {
		baseWait3TextTest(this, contains, expected);
	}
	// !WAIT

	// MATCH
	@Test(dataProvider = "matchText", dataProviderClass = TextAreaDP.class)
	public void waitTextMatchTest(final String regex, final String expected) {
		baseWaitTextMatchTest(this, regex, expected);
	}

	@Test(dataProvider = "matchText", dataProviderClass = TextAreaDP.class)
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

	// LINES
	@Test
	public void inputLinesTest() throws Exception {
		BaseScenarioInput.getJSExec().executeScript("arguments[0].value=\"\"", textElement().getWebElement());
		textElement().inputLines(IN[0], IN[1], IN[2]);
		final String[] out = BaseScenarioInput.getJSExec().executeScript("return arguments[0].value", textElement().getWebElement()).toString().split("\n");
		Assert.assertEquals(IN, out);
	}

	@Test
	public void addNewLineTest() throws Exception {
		BaseScenarioInput.getJSExec().executeScript("arguments[0].value=\"line1\\nline2\\nline3\"", textElement().getWebElement());
		final String textLine = "newLine";
		textElement().addNewLine(textLine);
		final String[] out = BaseScenarioInput.getJSExec().executeScript("return arguments[0].value", textElement().getWebElement()).toString().split("\n");
		Assert.assertEquals(textLine, out[3]);
	}

	@Test
	public void getLinesTest() throws Exception {
		BaseScenarioInput.getJSExec().executeScript("arguments[0].value=\"line1\\nline2\\nline3\"", textElement().getWebElement());
		Assert.assertEquals(textElement().getLines(), IN);
	}

	@Test
	public void getLinesLengthTest() throws Exception {
		BaseScenarioInput.getJSExec().executeScript("arguments[0].value=\"line1\\nline2\\nline3\"", textElement().getWebElement());
		Assert.assertTrue(textElement().getLines().length == 3);
	}
	// !LINES

	@Test
	public void focusTest() throws Exception {
		BaseScenarioInput.focusTest(this, "test123");
	}

	@Test
	public void getTextTest() {
		_onPage.open();
		baseGetTextTest(this, TEXT);
	}

	@Test
	public void getValueTest() {
		_onPage.open();
		checkText(textElement()::getValue, TEXT);
	}
	*/
}
