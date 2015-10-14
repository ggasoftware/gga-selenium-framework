package com.ggasoftware.uitest.control.interfaces.base;

import com.ggasoftware.uitest.control.base.apiInteract.GetElementModule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IBaseElement {
    WebDriver getDriver();

    By getLocator();

    void setAvatar(By byLocator, GetElementModule avatar);

    GetElementModule getAvatar();

    String getName();
}
