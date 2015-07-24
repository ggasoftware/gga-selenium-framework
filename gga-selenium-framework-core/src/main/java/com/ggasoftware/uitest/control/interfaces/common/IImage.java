package com.ggasoftware.uitest.control.interfaces.common;

import com.ggasoftware.uitest.control.base.annotations.JDIAction;
import com.ggasoftware.uitest.control.interfaces.base.IClickable;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public interface IImage<P> extends IClickable<P> {
    @JDIAction
    String getSource();
    @JDIAction
    String getAlt();
}
