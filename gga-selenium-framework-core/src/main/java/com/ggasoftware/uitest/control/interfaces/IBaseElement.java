package com.ggasoftware.uitest.control.interfaces;

import com.ggasoftware.uitest.control.apiInteract.GetElementModule;
import com.ggasoftware.uitest.utils.JDIAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IBaseElement {
    @JDIAction
    boolean isDisplayed();
    @JDIAction
    boolean waitDisplayed();
    @JDIAction
    boolean waitDisplayed(int seconds);
    @JDIAction
    boolean waitVanished();
    @JDIAction
    boolean waitVanished(int seconds);

    WebDriver getDriver();
    By getLocator();
    void setAvatar(GetElementModule avatar);
}
