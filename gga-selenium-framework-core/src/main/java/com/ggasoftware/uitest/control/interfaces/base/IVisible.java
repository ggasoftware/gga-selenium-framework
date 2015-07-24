package com.ggasoftware.uitest.control.interfaces.base;

import com.ggasoftware.uitest.control.base.annotations.JDIAction;

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
