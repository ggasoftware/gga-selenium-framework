package com.ggasoftware.uitest.control.interfaces.base;

import com.ggasoftware.uitest.control.base.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface ISetValue extends IHaveValue {
    @JDIAction
    void setValue(String value);
}
