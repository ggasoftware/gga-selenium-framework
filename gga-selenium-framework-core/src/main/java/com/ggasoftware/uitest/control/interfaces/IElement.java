package com.ggasoftware.uitest.control.interfaces;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IElement {
    String getName();
    By getLocator();
    WebElement getWebElement();
}
