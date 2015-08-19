package com.ggasoftware.jdi_ui_tests.core.elements.template.base;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IClickable;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public class Clickable extends Element implements IClickable {
    // Actions
    protected void clickAction();

    // Methods
    public final void click() { doJAction("Click on element", this::clickAction); }
}
