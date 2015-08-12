package com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base;

import java.util.List;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IBaseElement<TDriver, TLocator> {
    /** Get WebDriver associated with element */
    TDriver getDriver();
    /** Get Element’s locator */
    TLocator getLocator();
    void setLocator(TLocator locator);
    <T extends TLocator> void setContext(List<T> context);
    /** Get Element’s name */
    String getName();
}
