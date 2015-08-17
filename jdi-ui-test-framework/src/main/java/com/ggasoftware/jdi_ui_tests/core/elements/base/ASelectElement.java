package com.ggasoftware.jdi_ui_tests.core.elements.base;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.ISelect;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public abstract class ASelectElement extends AClickableText implements ISelect {
    // Actions
    protected abstract boolean isSelectedAction();

    // Methods
    public void select() { click(); }
    public final boolean isSelected() {
        return doJActionResult("Is Selected", this::isSelectedAction);
    }
}
