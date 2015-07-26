package com.epam.ui_test_framework.elements.interfaces.base;

import com.epam.ui_test_framework.elements.interfaces.common.IText;
import com.epam.ui_test_framework.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public interface ISelect extends IClickable, IText {
    @JDIAction
    boolean isSelected();
}
