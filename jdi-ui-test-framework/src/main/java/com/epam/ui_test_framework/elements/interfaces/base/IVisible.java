package com.epam.ui_test_framework.elements.interfaces.base;

import com.epam.ui_test_framework.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/26/2015.
 */
public interface IVisible {
    /** Waits while element becomes visible */
    @JDIAction
    boolean waitDisplayed();
    /** Waits while element becomes invisible */
    @JDIAction
    boolean waitVanished();
}
