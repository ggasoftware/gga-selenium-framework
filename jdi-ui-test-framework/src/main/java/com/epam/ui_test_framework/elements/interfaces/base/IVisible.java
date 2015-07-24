package com.epam.ui_test_framework.elements.interfaces.base;

import com.epam.ui_test_framework.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public interface IVisible {
    @JDIAction
    boolean isDisplayed();
    @JDIAction
    boolean waitDisplayed();
    @JDIAction
    boolean waitDisplayed(int seconds);
    @JDIAction
    boolean waitVanished();
    @JDIAction
    boolean waitVanished(int seconds);
}
