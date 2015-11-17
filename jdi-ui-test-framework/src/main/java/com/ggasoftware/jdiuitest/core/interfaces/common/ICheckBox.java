package com.ggasoftware.jdiuitest.core.interfaces.common;

import com.ggasoftware.jdiuitest.core.annotations.JDIAction;
import com.ggasoftware.jdiuitest.core.interfaces.base.IClickable;
import com.ggasoftware.jdiuitest.core.interfaces.base.ISetValue;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface ICheckBox extends IClickable, ISetValue {
    /** Set checkbox checked */
    @JDIAction
    void check();

    /** Set checkbox unchecked */
    @JDIAction
    void uncheck();

    /** @return Verify is checkbox checked */
    @JDIAction
    boolean isChecked();
}
