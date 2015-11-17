package com.ggasoftware.jdiuitest.core.interfaces.base;

import com.ggasoftware.jdiuitest.core.annotations.JDIAction;
import com.ggasoftware.jdiuitest.core.interfaces.common.IText;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public interface ISelect extends IClickable, IText {
    /** Selects Element. Similar to click() */
    @JDIAction
    void select();

    /** @return Checks is Element selected */
    @JDIAction
    boolean isSelected();
}
