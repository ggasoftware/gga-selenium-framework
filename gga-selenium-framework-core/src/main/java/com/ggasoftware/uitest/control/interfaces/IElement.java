package com.ggasoftware.uitest.control.interfaces;

import com.ggasoftware.uitest.utils.JDIAction;
import com.ggasoftware.uitest.utils.settings.HighlightSettings;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IElement extends IBaseElement {
    @JDIAction
    WebElement getWebElement();
    @JDIAction
    WebElement getWebElement(int timeouInSec);
    void highlight();
    void highlight(HighlightSettings settings);
    @JDIAction
    String getAttribute(String attributeName);
}
