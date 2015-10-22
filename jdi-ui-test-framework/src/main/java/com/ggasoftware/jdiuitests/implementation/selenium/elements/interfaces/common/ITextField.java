package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.ISetValue;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface ITextField extends ISetValue, IText, IElement {
    /**
     * @param text Specify text to input to TextField
     * @return Input text in textfield
     */
    @JDIAction
    void input(String text);

    /**
     * @param text Specify text to send keys to TextField
     * @return Input text in textfield
     */
    @JDIAction
    default void sendKeys(String text) {
        input(text);
    }

    /**
     * @param text Specify text to input to TextField
     * @return Clear and input text in textfield
     */
    @JDIAction
    void newInput(String text);

    /**
     * @return Clear textfield
     */
    @JDIAction
    void clear();

    /**
     * @return Focus(click) on textfield
     */
    @JDIAction
    void focus();
}
