package com.ggasoftware.jdiuitest.core.interfaces.base;

import com.ggasoftware.jdiuitest.core.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IClickable extends IElement {
    /** Click on Element */
    @JDIAction
    void click();
}
