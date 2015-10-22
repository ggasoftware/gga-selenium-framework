package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IBaseElement {
    /**
     * @return Get WebDriver associated with Element
     */
    WebDriver getDriver();

    /**
     * @return Get Element’s locator
     */
    By getLocator();

    /**
     * @return Get Element’s name
     */
    String getName();
}
