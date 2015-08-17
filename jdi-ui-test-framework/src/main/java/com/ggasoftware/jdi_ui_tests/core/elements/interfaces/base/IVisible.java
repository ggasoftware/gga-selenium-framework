package com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base;

import com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/26/2015.
 */
public interface IVisible {
    /** Waits while element becomes visible */
    @JDIAction
    boolean waitVisible();
    @JDIAction
    boolean waitDisplayed();
    /** Waits while element becomes invisible */
    @JDIAction
    boolean waitInvisible();
    @JDIAction
    boolean waitDisappear();
    @JDIAction
    boolean waitVanished();
}
