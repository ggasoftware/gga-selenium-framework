package com.epam.ui_test_framework.elements.interfaces.base;

import com.epam.ui_test_framework.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IClickable extends IElement {
    @JDIAction
    void click();
}
