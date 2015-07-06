package com.ggasoftware.uitest.control.base;

import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public abstract class HaveValue extends Element {
    public HaveValue() { }
    public HaveValue(By byLocator) { super(byLocator); }
    public HaveValue(String name, By byLocator) { super(name, byLocator); }

    protected String getValueAction() throws Exception { return "Get value not implemented"; }
    public final String getValue() throws Exception { return doJActionResult("Get value", this::getValueAction); }
}
