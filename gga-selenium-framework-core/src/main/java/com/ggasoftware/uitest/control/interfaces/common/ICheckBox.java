package com.ggasoftware.uitest.control.interfaces.common;


import com.ggasoftware.uitest.control.base.annotations.JDIAction;
import com.ggasoftware.uitest.control.interfaces.base.IClickable;
import com.ggasoftware.uitest.control.interfaces.base.ISetValue;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface ICheckBox<P> extends IClickable<P>, ISetValue {
    @JDIAction
    P check();

    @JDIAction
    P uncheck();

    @JDIAction
    boolean isChecked();
}
