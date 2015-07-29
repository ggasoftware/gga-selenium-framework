package com.epam.ui_test_framework.elements.interfaces.common;

import com.epam.ui_test_framework.elements.interfaces.base.IElement;
import com.epam.ui_test_framework.elements.interfaces.base.ISetValue;
import com.epam.ui_test_framework.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface ITextfield extends ISetValue, IText, IElement {
    /** Input text in textfield */
    @JDIAction
    void input(String text);
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
