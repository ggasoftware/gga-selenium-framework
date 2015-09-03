package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IClickable;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface ILink extends IClickable, IText {
    /** Get link destination */
    String getReference();
    /** Wait while link destination contains expected text. Returns link destination */
    @JDIAction
    String waitReference(String text);
    /** Wait while link destination contains expected text. Returns link destination */
    @JDIAction
    String waitMatchReference(String regEx);
}
