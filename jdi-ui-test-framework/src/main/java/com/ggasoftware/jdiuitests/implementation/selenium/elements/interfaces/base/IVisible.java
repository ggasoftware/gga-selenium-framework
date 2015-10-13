package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/26/2015.
 */
public interface IVisible {
    /** Check is Element visible */
    @JDIAction
    boolean isDisplayed();
    /** Check is Element hidden */
    @JDIAction
    boolean isHidden();
    /** Waits while Element becomes visible */
    @JDIAction
    boolean waitDisplayed();
    /** Waits while Element becomes invisible */
    @JDIAction
    boolean waitVanished();
}
