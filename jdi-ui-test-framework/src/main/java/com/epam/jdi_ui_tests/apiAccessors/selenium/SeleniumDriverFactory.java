package com.epam.jdi_ui_tests.apiAccessors.selenium;

import com.epam.jdi_ui_tests.elements.BaseElement;
import com.epam.jdi_ui_tests.elements.base.Element;
import com.epam.jdi_ui_tests.elements.interfaces.base.IElement;
import com.epam.jdi_ui_tests.settings.Drivers;
import com.epam.jdi_ui_tests.settings.HighlightSettings;
import com.epam.jdi_ui_tests.utils.linqInterfaces.JFuncT;
import com.epam.jdi_ui_tests.utils.linqInterfaces.JFuncTT;
import com.epam.jdi_ui_tests.utils.map.MapArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static com.epam.jdi_ui_tests.settings.Drivers.*;
import static com.epam.jdi_ui_tests.settings.FrameworkSettings.asserter;
import static com.epam.jdi_ui_tests.settings.FrameworkSettings.timeouts;
import static com.epam.jdi_ui_tests.utils.common.ReflectionUtils.isClass;
import static com.epam.jdi_ui_tests.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.String.format;
import static java.lang.System.setProperty;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.ie.InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS;
import static org.openqa.selenium.remote.DesiredCapabilities.internetExplorer;

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

    private String getDriversPath() throws IOException {
        return new File("src\\main\\resources").getCanonicalPath() + "\\";
    }

    private MapArray<String, JFuncT<WebDriver>> drivers = new MapArray<>();
    private MapArray<String, WebDriver> runDrivers = new MapArray<>();

    public void registerDriver(JFuncT<WebDriver> driver) {
        registerDriver("Driver" + drivers.size() + 1, driver);
    }

    public void registerDriver(WebDriver driver) {
        registerDriver(() -> driver);
    }
    public void registerDriver(Drivers driver)  {
        try {
            switch (driver) {
                case CHROME:
                    setProperty("webdriver.chrome.driver", getDriversPath() + "chromedriver.exe");
                    registerDriver(CHROME.toString(), ChromeDriver::new);
                    return;
                case FIREFOX:
                    registerDriver(FIREFOX.toString(), FirefoxDriver::new);
                    return;
                case IE:
                    DesiredCapabilities capabilities = internetExplorer();
                    capabilities.setCapability(INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                    setProperty("webdriver.ie.driver", getDriversPath() + "IEDriverServer.exe");
                    registerDriver(IE.toString(), () -> new InternetExplorerDriver(capabilities));
                    return;
            }
            asserter.exception("Unknown driver");
        } catch (Exception ex) { asserter.exception("Can't register web driver " + driver); }
    }

    public void registerDriver(String driverName, JFuncT<WebDriver> driver) {
        if (!drivers.add(driverName, driver))
            asserter.exception(format("Can't register Webdriver '%s'. Driver with same name already registered", driverName));
        currentDriverName = driverName;
    }

    public WebDriver getDriver() {
        return getDriver(currentDriverName);
    }

    public void reopenDriver() {
        if (runDrivers.keys().contains(currentDriverName)) {
            runDrivers.get(currentDriverName).close();
            runDrivers.removeByKey(currentDriverName);
        }
        if (drivers.keys().contains(currentDriverName))
            getDriver();
    }

    public WebDriver getDriver(String driverName) {
        try {
            if (runDrivers.keys().contains(driverName))
                return runDrivers.get(driverName);
            WebDriver resultDriver = tryGetResult(() -> drivers.get(driverName).invoke());
            runDrivers.add(driverName, resultDriver);
            if (resultDriver == null) {
                asserter.exception(format("Can't get Webdriver '%s'. This Driver name not registered", driverName));
                return null;
            }
            resultDriver.manage().window().maximize();
            resultDriver.manage().timeouts().implicitlyWait(timeouts.waitElementSec, SECONDS);
            return resultDriver;
        } catch (Exception ex) {
            asserter.exception("Can't get driver");
            return null;
        }
    }

    public void switchToDriver(String driverName) {
        if (drivers.keys().contains(driverName))
            currentDriverName = driverName;
        else
            asserter.exception(format("Can't switch to Webdriver '%s'. This Driver name not registered", driverName));
    }

    public String currentDriverName = "";

    public boolean isDemoMode = false;

    public void processDemoMode(BaseElement element) {
        if (isDemoMode)
            if (isClass(element.getClass(), Element.class))
                highlight((Element) element, highlightSettings);
    }

    public HighlightSettings highlightSettings = new HighlightSettings();

    public void highlight(IElement element) {
        highlight(element, highlightSettings);
    }

    public void highlight(IElement element, HighlightSettings highlightSettings) {
        if (highlightSettings == null)
            highlightSettings = new HighlightSettings();
        String orig = element.getWebElement().getAttribute("style");
        element.setAttribute("style", format("border: 3px solid %s; background-color: %s;", highlightSettings.FrameColor,
                highlightSettings.BgColor));
        try {
            Thread.sleep(highlightSettings.TimeoutInSec * 1000);
        } catch (Exception ignore) {
        }
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
