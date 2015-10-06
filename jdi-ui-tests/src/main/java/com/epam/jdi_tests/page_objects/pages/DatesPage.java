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
	@FindBy(xpath = "/html/body/div/div/main/div[2]/div/form/div[3]/div[2]/div[2]")
	public IFileInput _imageInput;
	@FindBy(xpath = "/html/body/div/div/main/div[2]/div/form/div[3]/div[2]/div[2]")
	public RFileInput _rImageInput;
}
