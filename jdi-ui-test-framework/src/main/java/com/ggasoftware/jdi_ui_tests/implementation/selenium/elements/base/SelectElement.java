package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.ISelect;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public class SelectElement extends ClickableText implements ISelect {
    public SelectElement() { }
    public SelectElement(By byLocator) { super(byLocator); }

    protected boolean isSelectedAction() { return getWebElement().isSelected(); }

    public void select() { click(); }
    public boolean isSelected() {
        return actions.isSelected(this::isSelectedAction);
    }
}
