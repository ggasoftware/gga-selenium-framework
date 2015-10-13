package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IClickable extends IElement {
    /** Click on Element */
    @JDIAction
    void click();
}
