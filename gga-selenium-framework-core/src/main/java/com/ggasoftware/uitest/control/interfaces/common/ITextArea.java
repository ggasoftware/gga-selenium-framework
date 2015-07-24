package com.ggasoftware.uitest.control.interfaces.common;


import com.ggasoftware.uitest.control.base.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface ITextArea<P> extends IInput<P> {
    @JDIAction
    String[] getLines();
}
