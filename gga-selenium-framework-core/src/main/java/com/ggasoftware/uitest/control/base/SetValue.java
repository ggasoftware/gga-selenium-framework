package com.ggasoftware.uitest.control.base;

import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public abstract class SetValue extends HaveValue {
    public SetValue() { }
    public SetValue(By byLocator) { super(byLocator); }
    public SetValue(String name, By byLocator) { super(name, byLocator); }

    protected void setValueAction(String value) throws Exception { }
    public final void setValue(String value) throws Exception { doJActionResult("Set value", this::getValueAction); }
}
