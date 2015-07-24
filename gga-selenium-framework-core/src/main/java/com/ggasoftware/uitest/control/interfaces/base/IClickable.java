package com.ggasoftware.uitest.control.interfaces.base;

import com.ggasoftware.uitest.control.base.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IClickable<P> extends IElement<P> {
    @JDIAction
    P click();
}
