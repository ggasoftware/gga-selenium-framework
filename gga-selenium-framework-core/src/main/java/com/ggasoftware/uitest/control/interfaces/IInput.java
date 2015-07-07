package com.ggasoftware.uitest.control.interfaces;

import com.ggasoftware.uitest.utils.JDIAction;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface IInput extends ISetValue {
    @JDIAction
    void input(String text);
    @JDIAction
    void newInput(String text);
    @JDIAction
    String getText();
    @JDIAction
    void clear();
    @JDIAction
    void focus();
}
