package com.epam.ui_test_framework.elements.interfaces.common;

import com.epam.ui_test_framework.elements.interfaces.base.IClickable;
import com.epam.ui_test_framework.utils.JDIAction;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public interface IImage extends IClickable {
    @JDIAction
    String getSource();
    @JDIAction
    String getAlt();
}
