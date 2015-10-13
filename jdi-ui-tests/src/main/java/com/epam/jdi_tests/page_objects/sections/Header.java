package com.epam.jdi_tests.page_objects.sections;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Section;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IImage;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Maksim_Palchevskii on 9/11/2015.
 */
public class Header extends Section {

    @FindBy(xpath = "//img[@src=\"images/Logo_Epam_Color.svg\"]")
    public IImage image;

}
