package com.epam.ui_test_framework.apiAccessors.selenium;

import com.epam.ui_test_framework.elements.BaseElement;
import com.epam.ui_test_framework.elements.base.Element;
import com.epam.ui_test_framework.elements.interfaces.base.IElement;
import com.epam.ui_test_framework.settings.Drivers;
import com.epam.ui_test_framework.settings.HighlightSettings;
import com.epam.ui_test_framework.utils.linqInterfaces.JFuncTT;
import com.epam.ui_test_framework.utils.map.MapArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.util.List;
import java.util.Set;

import static com.epam.ui_test_framework.settings.Drivers.CHROME;
import static com.epam.ui_test_framework.settings.FrameworkSettings.asserter;
import static com.epam.ui_test_framework.settings.FrameworkSettings.driverFactory;
import static com.epam.ui_test_framework.utils.common.ReflectionUtils.isClass;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public class SeleniumDriverFactory /*implements IAPIAvatar<WebElementAvatar>, WebDriver*/ {
    public SeleniumDriverFactory() {
        this(false, new HighlightSettings(), WebElement::isDisplayed);
    }
    public SeleniumDriverFactory(boolean isDemoMode) {
        this(isDemoMode, new HighlightSettings(), WebElement::isDisplayed);
    }
    public SeleniumDriverFactory(HighlightSettings highlightSettings) {
        this(false, highlightSettings, WebElement::isDisplayed);
    }
    public SeleniumDriverFactory(JFuncTT<WebElement, Boolean> elementSearchCriteria) {
        this(false, new HighlightSettings(), elementSearchCriteria);
    }
    public SeleniumDriverFactory(boolean isDemoMode, HighlightSettings highlightSettings,
             JFuncTT<WebElement, Boolean> elementSearchCriteria) {
        this.isDemoMode = isDemoMode;
        this.highlightSettings = highlightSettings;
        this.elementSearchCriteria = elementSearchCriteria;
    }

    public JFuncTT<WebElement, Boolean> elementSearchCriteria = WebElement::isDisplayed;

    private MapArray<String, WebDriver> drivers = new MapArray<>();
    public void registerDriver(WebDriver driver) {
        registerDriver("Driver" + drivers.size() + 1, driver);
    }
    public void registerDriver(Drivers drivers) {
        switch (drivers) {
            case CHROME:
                registerDriver(new ChromeDriver());
                return;
            case FIREFOX:
                registerDriver(new FirefoxDriver());
                return;
            case IE:
                registerDriver(new InternetExplorerDriver());
                return;
        }
        asserter.exception("Unknown driver");
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

    public boolean isDemoMode = false;
    public void processDemoMode(BaseElement element) {
        if (isDemoMode)
            if (isClass(element.getClass(), Element.class))
                highlight((Element)element, highlightSettings);
    }

    public HighlightSettings highlightSettings = new HighlightSettings();

    public void highlight(IElement element) { highlight(element, highlightSettings); }
    public void highlight(IElement element, HighlightSettings highlightSettings) {
        if (highlightSettings == null)
            highlightSettings = new HighlightSettings();
        String orig = element.getWebElement().getAttribute("style");
        element.setAttribute("style", format("border: 3px solid %s; background-color: %s;", highlightSettings.FrameColor,
                highlightSettings.BgColor));
        try { Thread.sleep(highlightSettings.TimeoutInSec * 1000); } catch (Exception ignore) {}
        element.setAttribute("style", orig);
    }

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
