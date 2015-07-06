package com.ggasoftware.uitest.control.interfaces;

import com.ggasoftware.uitest.utils.JDIAction;
import org.openqa.selenium.WebDriver;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IBaseElement {
    @JDIAction
    boolean isDisplayed() throws Exception;
    @JDIAction
    boolean waitDisplayed() throws Exception;
    @JDIAction
    boolean waitDisplayed(int seconds) throws Exception;
    @JDIAction
    boolean waitVanished() throws Exception;
    @JDIAction
    boolean waitVanished(int seconds) throws Exception;

    WebDriver getDriver() throws Exception;
}
