package com.epam.ui_test_framework.elements.interfaces.base;

import com.epam.ui_test_framework.utils.JDIAction;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface ISetValue extends IHaveValue {
    @JDIAction
    void setValue(String value);
}
