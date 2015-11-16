package com.ggasoftware.jdiuitest.core.interfaces.base;

import com.ggasoftware.jdiuitest.core.interfaces.common.IText;
import com.ggasoftware.jdiuitest.core.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public interface ISelect extends IClickable, IText {
    /**
     * @return Selects Element. Similar to click()
     */
    @JDIAction
    void select();

    /**
     * @return Checks is Element selected
     */
    @JDIAction
    boolean isSelected();
}
