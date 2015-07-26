package com.epam.ui_test_framework.elements.interfaces.base;

import com.epam.ui_test_framework.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/26/2015.
 */
public interface IVisible {
    @JDIAction
    boolean waitDisplayed();
    @JDIAction
    boolean waitVanished();
}
