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
    boolean isDisplayed() throws Exception;
    @JDIAction
    boolean waitDisplayed() throws Exception;
    @JDIAction
    boolean waitDisplayed(int seconds) throws Exception;
    @JDIAction
    boolean waitVanished() throws Exception;
    @JDIAction
    boolean waitVanished(int seconds) throws Exception;
    void highlight() throws Exception;
    void highlight(HighlightSettings settings) throws Exception;
    @JDIAction
    String getAttribute(String attributeName);
}
