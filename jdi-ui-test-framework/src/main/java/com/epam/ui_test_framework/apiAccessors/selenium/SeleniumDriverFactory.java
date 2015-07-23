package com.epam.ui_test_framework.apiAccessors.selenium;

import com.epam.ui_test_framework.utils.map.MapArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

import static com.epam.ui_test_framework.settings.FrameworkSettings.asserter;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public class SeleniumDriverFactory /*implements IAPIAvatar<WebElementAvatar>, WebDriver*/ {
    private MapArray<String, WebDriver> drivers = new MapArray<>();
    public void registerDriver(WebDriver driver) {
        registerDriver("Driver" + drivers.size() + 1, driver);
    }
    public void registerDriver(String driverName, WebDriver driver) {
        if (!drivers.add(driverName, driver))
            asserter.exception(format("Can't register Webdriver '%s'. Driver with same name already registered", driverName));
        currentDriverName = driverName;
    }
    public WebDriver getDriver() {
        return getDriver(currentDriverName);
    }
    public WebDriver getDriver(String driverName) {
        WebDriver resultDriver = drivers.get(driverName);
        if (resultDriver == null) {
            asserter.exception(format("Can't get Webdriver '%s'. This Driver name not registered", driverName));
            return null;
        }
        return resultDriver;
    }
    public void switchToDriver(String driverName) {
        WebDriver driver = drivers.get(driverName);
        if (driver != null)
            currentDriverName = driverName;
        else
            asserter.exception(format("Can't switch to Webdriver '%s'. This Driver name not registered", driverName));
    }
    public String currentDriverName = "";

    public void runApplication() {

    }

    public void closeApplication() {

    }

    public void get(String s) {

    }

    public String getCurrentUrl() {
        return null;
    }

    public String getTitle() {
        return null;
    }

    public List<WebElement> findElements(By by) {
        return null;
    }

    public WebElement findElement(By by) {
        return null;
    }

    public String getPageSource() {
        return null;
    }

    public void close() {

    }

    public void quit() {

    }

    public Set<String> getWindowHandles() {
        return null;
    }

    public String getWindowHandle() {
        return null;
    }
}
