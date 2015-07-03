package com.ggasoftware.uitest.control.interfaces;

import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IBaseElement {
    String getName();
    By getByLocator();
    boolean isDisplayed();
    boolean isPresent();
}
