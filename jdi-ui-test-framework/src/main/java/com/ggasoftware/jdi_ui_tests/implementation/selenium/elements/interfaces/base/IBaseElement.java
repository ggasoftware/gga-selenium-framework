package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IBaseElement {
    /** Get WebDriver associated with element
     *
     * @return
     */
    WebDriver getDriver();
    /** Get Element’s locator */
    By getLocator();
    /** Get Element’s name */
    String getName();
}
