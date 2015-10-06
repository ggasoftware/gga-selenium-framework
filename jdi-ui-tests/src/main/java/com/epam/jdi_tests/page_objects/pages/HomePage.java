package com.epam.jdi_tests.page_objects.pages;

import org.openqa.selenium.support.FindBy;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite.Page;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IImage;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IText;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */

public class HomePage extends Page {
	@FindBy(css = ".main-txt")
	public IText text;


	@FindBy(id = "epam_logo")
	public IImage logoImage;
}
