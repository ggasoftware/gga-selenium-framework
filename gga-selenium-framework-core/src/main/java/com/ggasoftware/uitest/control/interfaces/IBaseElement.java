package com.ggasoftware.uitest.control.interfaces;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IBaseElement {
    String getName();
    By getByLocator();
    boolean isDisplayed() throws Exception;
    boolean isPresent();

    WebDriver getDriver() throws Exception;
}
