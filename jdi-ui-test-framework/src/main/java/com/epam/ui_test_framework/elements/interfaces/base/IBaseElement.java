package com.epam.ui_test_framework.elements.interfaces.base;

import com.epam.ui_test_framework.elements.apiInteract.GetElementModule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IBaseElement {
    WebDriver getDriver();
    By getLocator();
    void setAvatar(GetElementModule avatar);
    void setAvatar(By byLocator, GetElementModule avatar);
    GetElementModule getAvatar();
    String getName();
}
