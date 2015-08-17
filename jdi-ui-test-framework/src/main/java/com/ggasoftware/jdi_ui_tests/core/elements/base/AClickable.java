package com.ggasoftware.jdi_ui_tests.core.elements.base;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IClickable;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public abstract class AClickable extends ABaseElement implements IClickable {
    // Actions
    protected abstract void clickAction();

    // Methods
    public final void click() { doJAction("Click on element", this::clickAction); }
}
