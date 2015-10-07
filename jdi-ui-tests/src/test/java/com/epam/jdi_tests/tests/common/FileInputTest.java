package com.epam.jdi_tests.tests.common;

import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import java.lang.reflect.Method;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.enums.Preconditions;
import com.epam.jdi_tests.page_objects.EpamJDISite;
import com.ggasoftware.jdi_ui_tests.implementation.robot.elements.common.RFileInput;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IFileInput;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;
import static com.epam.jdi_tests.tests.complex.CommonActionsData.*;

// TODO 
public class FileInputTest extends InitTests {

	public FileInputTest() {
		_onPage = Preconditions.DATES_PAGE;
	}

	@BeforeMethod
	public void before(final Method method) throws Exception {
		isInState(_onPage, method);
	}

	@Override
	public RFileInput textElement() {
		return EpamJDISite.dates._rImageInput;
	}

	public IFileInput robotInput() {
		return EpamJDISite.dates._imageInput;
	}
	
	// INPUT
	@Test
	public void inputTest() {
		textElement().input(getFPath());
		checkAction("FileUpload: file \""+ getFName() +"\" has been uploaded");
	}
	
	@Test
	public void sendKeysTest() {
		textElement().sendKeys(getFPath());
		checkAction("FileUpload: file \""+ getFName() +"\" has been uploaded");
	}
	
	@Test
	public void newInputTest() throws Exception {
		textElement().newInput(getFPath());
		checkAction("FileUpload: file \""+ getFName() +"\" has been uploaded");
	}
	// !INPUT
	
}
