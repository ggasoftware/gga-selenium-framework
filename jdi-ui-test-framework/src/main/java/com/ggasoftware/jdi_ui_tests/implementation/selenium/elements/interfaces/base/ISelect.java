package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IText;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public interface ISelect extends IClickable, IText {
    /** Selects webElement. Similar to click() */
    @JDIAction
    void select();
    /** Checks is Element selected */
    @JDIAction
    boolean isSelected();
}
