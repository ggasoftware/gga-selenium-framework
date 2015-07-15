package com.epam.ui_test_framework.elements.interfaces.common;

import com.epam.ui_test_framework.utils.JDIAction;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface ITextArea extends IInput {
    @JDIAction
    String[] getLines();
}
