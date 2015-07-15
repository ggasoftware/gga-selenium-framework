package com.epam.ui_test_framework.elements.interfaces.common;

import com.epam.ui_test_framework.elements.interfaces.base.IElement;
import com.epam.ui_test_framework.elements.interfaces.base.IHaveValue;
import com.epam.ui_test_framework.utils.JDIAction;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IText extends IHaveValue, IElement {
    @JDIAction
    String getText();
    @JDIAction
    String waitText(String text);
    @JDIAction
    String waitMatchText(String regEx);
}
