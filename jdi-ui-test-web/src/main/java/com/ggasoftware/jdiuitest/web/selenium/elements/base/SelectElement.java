package com.ggasoftware.jdiuitest.web.selenium.elements.base;

import com.ggasoftware.jdiuitest.core.interfaces.base.ISelect;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public class SelectElement extends ClickableText implements ISelect {
    public SelectElement() {
    }

    public SelectElement(By byLocator) {
        super(byLocator);
    }

    public SelectElement(WebElement webElement) {
        super(webElement);
    }

    protected boolean isSelectedAction() {
        return getWebElement().isSelected();
    }

    public void select() {
        click();
    }

    public boolean isSelected() {
        return actions.isSelected(this::isSelectedAction);
    }
}
