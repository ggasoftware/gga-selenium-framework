package com.ggasoftware.jdiuitest.core.interfaces.common;


import com.ggasoftware.jdiuitest.core.annotations.JDIAction;
import com.ggasoftware.jdiuitest.core.interfaces.base.IElement;
import com.ggasoftware.jdiuitest.core.interfaces.base.ISetValue;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface ITextField extends ISetValue, IText, IElement {
    /**
     * @param text Specify text to input to TextField
     * Input text in textfield
     */
    @JDIAction
    void input(String text);

    /**
     * @param text Specify text to send keys to TextField
     * Input text in textfield
     */
    @JDIAction
    default void sendKeys(String text) {
        input(text);
    }

    /**
     * @param text Specify text to input to TextField
     * Clear and input text in textfield
     */
    @JDIAction
    void newInput(String text);

    /** Clear textfield */
    @JDIAction
    void clear();

    /** Focus(click) on textfield */
    @JDIAction
    void focus();
}
