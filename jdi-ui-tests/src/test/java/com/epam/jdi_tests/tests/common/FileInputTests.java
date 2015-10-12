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
import static com.epam.jdi_tests.tests.complex.CommonActionsData.*;

public class FileInputTests extends InitTests {
	private Preconditions _onPage = null;
	public FileInputTests() {
		_onPage = Preconditions.DATES_PAGE;
	}

	public RFileInput input() {
		return EpamJDISite.dates.rImageInput;
	}

	public IFileInput robotInput() {
		return EpamJDISite.dates.imageInput;
	}
	
	@BeforeMethod
	public void before(final Method method) {
		isInState(_onPage, method);
	}
	
	// INPUT
	@Test
	public void inputTest() {
		input().input(getFPath());
		checkAction("FileUpload: file \""+ getFName() +"\" has been uploaded");
	}
	
	@Test
	public void sendKeysTest() {
		input().sendKeys(getFPath());
		checkAction("FileUpload: file \""+ getFName() +"\" has been uploaded");
	}
	
	@Test
	public void newInputTest() throws Exception {
		input().newInput(getFPath());
		checkAction("FileUpload: file \""+ getFName() +"\" has been uploaded");
	}
	// !INPUT
	
}
