package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.ISetValue;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface ITextField extends ISetValue, IText, IElement {
    /** Input text in textfield */
    @JDIAction
    void input(String text);
    /** Input text in textfield */
    @JDIAction
    default void sendKeys(String text) { input(text); }
    /** Clear and input text in textfield */
    @JDIAction
    void newInput(String text);
    /** Clear textfield */
    @JDIAction
    void clear();
    /** Focus(click) on textfield */
    @JDIAction
    void focus();
}
