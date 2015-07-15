package com.epam.ui_test_framework.elements.interfaces.base;

import com.epam.ui_test_framework.utils.JDIAction;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public interface ISelect extends IClickableText {
    @JDIAction
    boolean isSelected();
}
