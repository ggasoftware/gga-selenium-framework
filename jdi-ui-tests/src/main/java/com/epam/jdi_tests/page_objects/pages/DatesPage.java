package com.epam.jdi_tests.page_objects.pages;

import org.openqa.selenium.support.FindBy;

import com.ggasoftware.jdi_ui_tests.implementation.robot.elements.common.RFileInput;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite.Page;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IDatePicker;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IFileInput;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */
public class DatesPage extends Page {

	@FindBy(xpath = "//*[@id=\"datepicker\"]/input")
	public IDatePicker _datepicker;
	@FindBy(xpath = ".//div[@data-provides=\"fileinput\"]")
	public IFileInput _imageInput;
	@FindBy(xpath = ".//div[@data-provides=\"fileinput\"]")
	public RFileInput _rImageInput;
}
