package com.epam.jditests.pageobjects.pages;

import com.ggasoftware.jdiuitest.core.interfaces.common.IImage;
import com.ggasoftware.jdiuitest.web.selenium.elements.common.Text;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.WebPage;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Maksim_Palchevskii on 8/17/2015.
 */

public class HomePage extends WebPage {
    @FindBy(css = ".main-txt")
    public Text text;
    @FindBy(css = ".epam-logo img")
    public IImage logoImage;
}
