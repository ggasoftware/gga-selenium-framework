package com.ggasoftware.uitest.control.interfaces.common;


import com.ggasoftware.uitest.control.base.annotations.JDIAction;
import com.ggasoftware.uitest.control.interfaces.base.IElement;
import com.ggasoftware.uitest.control.interfaces.base.ISetValue;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface IInput<P> extends ISetValue, IText<P>, IElement<P> {
    @JDIAction
    void input(String text);
    @JDIAction
    void newInput(String text);
    @JDIAction
    P clear();
    @JDIAction
    P focus();
}
