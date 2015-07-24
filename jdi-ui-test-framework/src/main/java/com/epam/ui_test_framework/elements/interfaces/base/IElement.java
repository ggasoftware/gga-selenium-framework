package com.epam.ui_test_framework.elements.interfaces.base;

import com.epam.ui_test_framework.elements.page_objects.annotations.JDIAction;
import org.openqa.selenium.WebElement;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IElement extends IBaseElement, IVisible {
    @JDIAction
    WebElement getWebElement();
    @JDIAction
    WebElement getWebElement(int timeouInSec);
    @JDIAction
    String getAttribute(String attributeName);
    void setAttribute(String attributeName, String value);
}
