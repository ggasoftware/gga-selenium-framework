package com.ggasoftware.uitest.control.interfaces.base;

import com.ggasoftware.uitest.control.base.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public interface ISelect<P> extends IClickableText<P> {
    @JDIAction
    boolean isSelected();
}
