package com.epam.jditests.pageobjects.sections;

import com.ggasoftware.jdiuitest.core.interfaces.common.IImage;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.Section;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Maksim_Palchevskii on 9/11/2015.
 */
public class Header extends Section {

    @FindBy(xpath = "//img[@src=\"images/Logo_Epam_Color.svg\"]")
    public IImage image;

}
