package com.ggasoftware.uitest.control.interfaces;

import com.ggasoftware.uitest.utils.JDIAction;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface ISetValue extends IHaveValue {
    @JDIAction
    void setValue(String value) throws Exception;
}
