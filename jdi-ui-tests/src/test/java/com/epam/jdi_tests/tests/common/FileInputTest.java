package com.epam.jdi_tests.tests.common;

import static com.epam.jdi_tests.page_objects.EpamJDISite.isInState;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.jdi_tests.InitTests;
import com.epam.jdi_tests.enums.Preconditions;
import com.epam.jdi_tests.page_objects.EpamJDISite;
import com.ggasoftware.jdi_ui_tests.implementation.robot.elements.common.RFileInput;

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
	public RFileInput textElement() throws Exception {
		return EpamJDISite.dates._imageInput;
	}
	
	@Test
	public void testName() throws Exception {
//		new File("C:/file.txt");
//		decode(new File(getClass().getResource("").getPath()) + "\\test.txt", "UTF-8");
		System.out.println("");
	}
}
