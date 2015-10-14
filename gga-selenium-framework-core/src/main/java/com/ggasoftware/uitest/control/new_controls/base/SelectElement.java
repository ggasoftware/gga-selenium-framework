package com.ggasoftware.uitest.control.new_controls.base;

import com.ggasoftware.uitest.control.interfaces.base.ISelect;
import com.ggasoftware.uitest.control.new_controls.common.ClickableText;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public class SelectElement<P> extends ClickableText<P> implements ISelect<P> {
    public SelectElement() {
    }

    public SelectElement(By byLocator) {
        super(byLocator);
    }

    public void select() {
        click();
    }

    protected boolean isSelectedAction() {
        return getWebElement().isSelected();
    }

    public final boolean isSelected() {
        return doJActionResult("Is Selected", this::isSelectedAction);
    }
}
