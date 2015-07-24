package com.epam.ui_test_framework.elements.interfaces.common;

import com.epam.ui_test_framework.elements.interfaces.base.IElement;
import com.epam.ui_test_framework.elements.interfaces.base.ISetValue;
import com.epam.ui_test_framework.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface IInput extends ISetValue, IText, IElement {
    @JDIAction
    void input(String text);
    @JDIAction
    void newInput(String text);
    @JDIAction
    void clear();
    @JDIAction
    void focus();
}
