package com.epam.jdi_ui_tests.elements.interfaces.base;

import com.epam.jdi_ui_tests.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IClickable extends IElement {
    /** Click on element */
    @JDIAction
    void click();
}
