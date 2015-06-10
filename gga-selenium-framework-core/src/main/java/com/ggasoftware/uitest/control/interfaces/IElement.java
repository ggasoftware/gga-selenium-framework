package com.ggasoftware.uitest.control.interfaces;

import com.ggasoftware.uitest.utils.HighlightSettings;
import com.ggasoftware.uitest.utils.JDIAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.internal.annotations.IBaseBeforeAfter;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IElement extends IBaseElement {
    @JDIAction
    WebElement getWebElement();
    @JDIAction
    boolean isDisplayed();

    void highlight();

    void highlight(HighlightSettings highlightSettings);
    @JDIAction
    String getAttribute(String attributeName);
}
