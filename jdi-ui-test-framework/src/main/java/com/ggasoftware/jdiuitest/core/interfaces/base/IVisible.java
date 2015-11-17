package com.ggasoftware.jdiuitest.core.interfaces.base;

import com.ggasoftware.jdiuitest.core.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/26/2015.
 */
public interface IVisible {
    /** @return Check is Element visible */
    @JDIAction
    boolean isDisplayed();

    /** @return Check is Element hidden */
    @JDIAction
    boolean isHidden();

    /** @return Waits while Element becomes visible */
    @JDIAction
    boolean waitDisplayed();

    /** @return Waits while Element becomes invisible */
    @JDIAction
    boolean waitVanished();
}
