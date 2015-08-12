package com.ggasoftware.jdi_ui_tests.core.elements.base;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IClickable;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public abstract class Clickable extends Element implements IClickable {
    public Clickable() { }
    public Clickable(By byLocator) { super(byLocator); }

    abstract void clickAction();
    public final void click() { doJAction("Click on element", this::clickAction); }
}
