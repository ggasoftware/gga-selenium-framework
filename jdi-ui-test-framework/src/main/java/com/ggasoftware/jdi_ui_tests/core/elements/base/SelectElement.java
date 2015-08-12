package com.ggasoftware.jdi_ui_tests.core.elements.base;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.ISelect;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public class SelectElement extends ClickableText implements ISelect {
    public SelectElement() { }
    public SelectElement(By byLocator) { super(byLocator); }

    public void select() { click(); }
    protected boolean isSelectedAction() { return getWebElement().isSelected(); }
    public final boolean isSelected() {
        return doJActionResult("Is Selected", this::isSelectedAction);
    }
}
