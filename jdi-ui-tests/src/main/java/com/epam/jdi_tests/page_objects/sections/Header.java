package com.epam.jdi_tests.page_objects.sections;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite.Section;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IImage;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Maksim_Palchevskii on 9/11/2015.
 */
public class Header extends Section {

    @FindBy(xpath = "//img[@src=\"images/Logo_Epam_Color.svg\"]")
    public IImage image;

}
