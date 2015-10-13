package com.ggasoftware.jdiuitests.implementation.selenium.driver;

import com.ggasoftware.jdiuitests.core.settings.HighlightSettings;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdiuitests.core.utils.map.MapArray;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.util.List;
import java.util.Set;

import static com.ggasoftware.jdiuitests.core.settings.JDISettings.*;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.timeouts;
import static com.ggasoftware.jdiuitests.core.utils.common.ReflectionUtils.isClass;
import static com.ggasoftware.jdiuitests.core.utils.common.Timer.sleep;
import static com.ggasoftware.jdiuitests.implementation.selenium.driver.DriverTypes.*;
import static com.ggasoftware.jdiuitests.implementation.selenium.driver.RunTypes.LOCAL;
import static com.ggasoftware.jdiuitests.implementation.selenium.driver.RunTypes.SAUCE_LAB;
import static com.ggasoftware.jdiuitests.implementation.selenium.driver.SauceLabRunner.getSauceDesiredCapabilities;
import static com.ggasoftware.jdiuitests.implementation.selenium.driver.SauceLabRunner.getSauceUrl;
import static java.lang.String.format;
import static java.lang.System.setProperty;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.openqa.selenium.ie.InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS;
import static org.openqa.selenium.remote.DesiredCapabilities.internetExplorer;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public class SeleniumDriverFactory /*implements JDriver<WebElementAvatar>, WebDriver*/ {
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

    private MapArray<String, JFuncT<WebDriver>> drivers = new MapArray<>();
    private MapArray<String, WebDriver> runDrivers = new MapArray<>();
    public boolean hasDrivers() { return drivers.size() > 0; }
    public void registerDriver(JFuncT<WebDriver> driver) {
        registerDriver("Driver" + drivers.size() + 1, driver);
    }
    public JFuncTT<WebElement, Boolean> elementSearchCriteria = WebElement::isDisplayed;
    public RunTypes runType = LOCAL;
    public void setRunType(String runType){
        switch (runType.toLowerCase()) {
            case "local":
                this.runType = LOCAL; break;
            case "sauce lab": case "saucelab": case "sauce_lab":
                this.runType = SAUCE_LAB; break;
        }
    }
    public String driversPath = "src\\main\\resources";

    private String getDriversPath() {
        return asserter.silent(() -> new File(driversPath).getCanonicalPath() + "\\");
    }

    // REGISTER DRIVER

    public void registerDriver(String driverName) {
        switch (driverName.toLowerCase()) {
            case "chrome":
                registerDriver(CHROME); return;
            case "firefox":
                registerDriver(FIREFOX); return;
            case "ie": case "internetexplorer":
                registerDriver(IE); return;
            default:
                throw exception("Unknown driver: " + driverName);
        }
    }

    public void registerDriver(DriverTypes driverType)  {
        switch (runType) {
            case LOCAL:
                registerLocalDriver(driverType); return;
            case SAUCE_LAB:
                registerDriver("SauceLab " + driverType,
                        () -> new RemoteWebDriver(getSauceUrl(), getSauceDesiredCapabilities(driverType)));
                return;
        }
        throw exception("Unknown driver: " + driverType);
    }

    private void registerLocalDriver(DriverTypes driverType)  {
        switch (driverType) {
            case CHROME:
                setProperty("webdriver.chrome.driver", getDriversPath() + "chromedriver.exe");
                registerDriver(getDriverName(CHROME), ChromeDriver::new);
                return;
            case FIREFOX:
                registerDriver(getDriverName(FIREFOX), FirefoxDriver::new);
                return;
            case IE:
                DesiredCapabilities capabilities = internetExplorer();
                capabilities.setCapability(INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                setProperty("webdriver.ie.driver", getDriversPath() + "IEDriverServer.exe");
                registerDriver(getDriverName(IE), () -> new InternetExplorerDriver(capabilities));
                return;
        }
        throw exception("Unknown driver: " + driverType);
    }

    private String getDriverName(DriverTypes driverType) {
        int numerator = 2;
        String driverName = driverType.toString();
        while (drivers.keys().contains(driverName))
            driverName = driverName + numerator++;
        return driverName;
    }

    public void registerDriver(String driverName, JFuncT<WebDriver> driver) {
        if (!drivers.add(driverName, driver))
            throw exception("Can't register Webdriver '%s'. Driver with same name already registered", driverName);
        currentDriverName = driverName;
    }

    // GET DRIVER

    public WebDriver getDriver() {
        if (!currentDriverName.equals(""))
            return getDriver(currentDriverName);
        registerDriver(CHROME);
        return getDriver(CHROME.toString());
    }
    public WebDriver getDriver(String driverName) {
        try {
            if (runDrivers.keys().contains(driverName))
                return runDrivers.get(driverName);
            WebDriver resultDriver = drivers.get(driverName).invoke();
            runDrivers.add(driverName, resultDriver);
            if (resultDriver == null)
                throw exception("Can't get Webdriver '%s'. This Driver name not registered", driverName);
            resultDriver.manage().window().maximize();
            resultDriver.manage().timeouts().implicitlyWait(timeouts.waitElementSec, SECONDS);
            return resultDriver;
        } catch (Throwable ex) { throw exception("Can't get driver"); }
    }
    public void reopenDriver() {
        if (runDrivers.keys().contains(currentDriverName)) {
            runDrivers.get(currentDriverName).close();
            runDrivers.removeByKey(currentDriverName);
        }
        if (drivers.keys().contains(currentDriverName))
            getDriver();
    }
    public void switchToDriver(String driverName) {
        if (drivers.keys().contains(driverName))
            currentDriverName = driverName;
        else
            throw exception("Can't switch to Webdriver '%s'. This Driver name not registered", driverName);
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
        sleep(highlightSettings.TimeoutInSec * 1000);
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
