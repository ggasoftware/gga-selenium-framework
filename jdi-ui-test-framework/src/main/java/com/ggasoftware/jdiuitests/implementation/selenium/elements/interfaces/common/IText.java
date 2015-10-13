package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IHasValue;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IText extends IHasValue, IElement {
    /** Get Element’s text */
    @JDIAction
    String getText();
    /** Wait while Element’s text contains expected text. Returns Element’s text */
    @JDIAction
    String waitText(String text);
    /** Wait while Element’s text matches regEx. Returns Element’s text */
    @JDIAction
    String waitMatchText(String regEx);
}
