package com.epam.jditests.pageobjects.pages;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IImage;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IText;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */

public class HomePage extends Page {
    @FindBy(css = ".main-txt")
    public IText text;
    @FindBy(css = ".epam-logo img")
    public IImage logoImage;
}
