package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IText;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JDIAction;

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
