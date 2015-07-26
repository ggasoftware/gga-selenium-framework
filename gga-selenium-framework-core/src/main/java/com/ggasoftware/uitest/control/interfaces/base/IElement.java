package com.ggasoftware.uitest.control.interfaces.base;

import com.ggasoftware.uitest.control.base.annotations.JDIAction;
import org.openqa.selenium.WebElement;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IElement<P> extends IBaseElement, IVisible {
    @JDIAction
    WebElement getWebElement();
    @JDIAction
    boolean waitAttribute(String name, String value);
    @JDIAction
    P setAttributeJS(String attributeName, String value);
}
