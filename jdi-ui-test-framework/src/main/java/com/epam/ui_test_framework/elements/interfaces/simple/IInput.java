package com.epam.ui_test_framework.elements.interfaces.simple;

import com.epam.ui_test_framework.elements.interfaces.base.IElement;
import com.epam.ui_test_framework.elements.interfaces.base.ISetValue;
import com.epam.ui_test_framework.utils.JDIAction;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface IInput extends ISetValue, IElement {
    @JDIAction
    void input(String text);
    @JDIAction
    void newInput(String text);
    @JDIAction
    void input(Object obj);
    @JDIAction
    void newInput(Object obj);
    @JDIAction
    String getText();
    @JDIAction
    void clear();
    @JDIAction
    void focus();
}
