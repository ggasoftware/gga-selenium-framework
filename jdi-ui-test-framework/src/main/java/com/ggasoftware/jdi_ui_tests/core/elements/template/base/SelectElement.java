package com.ggasoftware.jdi_ui_tests.core.elements.template.base;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.ISelect;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public class SelectElement extends ClickableText implements ISelect {
    // Actions
    protected boolean isSelectedAction();

    // Methods
    public void select() { click(); }
    public final boolean isSelected() {
        return doJActionResult("Is Selected", this::isSelectedAction);
    }
}
