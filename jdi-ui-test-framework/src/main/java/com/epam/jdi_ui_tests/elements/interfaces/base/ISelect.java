package com.epam.jdi_ui_tests.elements.interfaces.base;

import com.epam.jdi_ui_tests.elements.interfaces.common.IText;
import com.epam.jdi_ui_tests.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public interface ISelect extends IClickable, IText {
    /** Selects element. Similar to click() */
    @JDIAction
    void select();
    /** Checks is Element selected */
    @JDIAction
    boolean isSelected();
}
