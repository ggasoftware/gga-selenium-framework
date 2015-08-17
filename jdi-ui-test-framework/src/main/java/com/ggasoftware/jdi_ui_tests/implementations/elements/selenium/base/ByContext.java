package com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.base;

import org.openqa.selenium.By;

import static com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.base.ContextType.Locator;

/**
 * Created by Roman_Iovlev on 8/11/2015.
 */
public class ByContext {
    public ContextType type;
    public By byLocator;
    public ByContext(ContextType type, By byLocator) {
        this.byLocator = byLocator;
        this.type = type;
    }
    public ByContext(By byLocator) {
        this.byLocator = byLocator;
        this.type = Locator;
    }
}
