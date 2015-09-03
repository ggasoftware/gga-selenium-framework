package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/26/2015.
 */
public interface IVisible {
    /** Waits while webElement becomes visible */
    @JDIAction
    boolean waitDisplayed();
    /** Waits while webElement becomes invisible */
    @JDIAction
    boolean waitVanished();
}
