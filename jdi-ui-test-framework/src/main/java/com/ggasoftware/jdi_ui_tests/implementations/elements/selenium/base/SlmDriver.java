package com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.base;

import com.ggasoftware.jdi_ui_tests.core.elements.base.IDriver;
import com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.DriverTypes;
import com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.RunTypes;
import com.ggasoftware.jdi_ui_tests.core.settings.JDISettings;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

import static com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.DriverTypes.*;
import static com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.RemoteRunner.getRemoteDriver;
import static com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.RunTypes.*;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.*;
import static java.lang.System.setProperty;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.eclipse.jetty.util.TypeUtil.asList;
import static org.openqa.selenium.ie.InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS;
import static org.openqa.selenium.remote.DesiredCapabilities.internetExplorer;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public class SlmDriver implements IDriver<WebDriver> {
    private JFuncT<WebDriver> driverFunc;
    private WebDriver webDriver;
    public SlmDriver(JFuncT<WebDriver> driverFunc) {
        this.driverFunc = driverFunc;
    }
    public SlmDriver(DriverTypes driverType) {
        this.driverFunc = get(driverType);
    }
    public SlmDriver(String driverName) {
        if (!isSupported(driverName))
            throw asserter.exception("Unknown driver: " + driverName);
        switch (driverName.toLowerCase()) {
            case "chrome":
                this.driverFunc = get(CHROME);
            case "firefox":
                this.driverFunc = get(FIREFOX);
            case "ie": case "internet explorer":
                this.driverFunc = get(IE);
            case "opera":
                this.driverFunc = get(OPERA);
            case "safari":
                this.driverFunc = get(SAFARI);
        }
    }
    private static String[] supportedDrivers = new String[] { "chrome", "firefox", "ie", "internet explorer", "opera", "safari"};
    public static boolean isSupported(String driverName) { return asList(supportedDrivers).contains(driverName.toLowerCase());}

    public WebDriver get() throws Exception {
        webDriver = driverFunc.invoke();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(JDISettings.timeouts.waitElementSec, SECONDS);
        return webDriver;
    }

    private JFuncT<WebDriver> getLocalDriver(DriverTypes driverType)  {
        switch (driverType) {
            case CHROME:
                setProperty("webdriver.chrome.driver", getDriversPath() + "chromedriver.exe");
                return ChromeDriver::new;
            case FIREFOX:
                return FirefoxDriver::new;
            case IE:
                DesiredCapabilities capabilities = internetExplorer();
                capabilities.setCapability(INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                setProperty("webdriver.ie.driver", getDriversPath() + "IEDriverServer.exe");
                return () -> new InternetExplorerDriver(capabilities);
            case OPERA:
                return OperaDriver::new;
            case SAFARI:
                return SafariDriver::new;
        }
        throw asserter.exception("Unknown driver: " + driverType);
    }

    public static RunTypes runType = LOCAL;
    public static void setRunType(String runType){
        switch (runType.toLowerCase()) {
            case "local":
                SlmDriver.runType = LOCAL; break;
            case "sauce lab": case "saucelab": case "sauce_lab":
                SlmDriver.runType = SAUCE_LAB; break;
        }
    }
    private String getDriversPath() {
        return asserter.silent(() -> new File("src\\main\\resources").getCanonicalPath() + "\\");
    }

    // REGISTER DRIVER

    public JFuncT<WebDriver> get(DriverTypes driverType)  {
        switch (runType) {
            case LOCAL:
                return getLocalDriver(driverType);
            case SAUCE_LAB:
                return  () -> getRemoteDriver(driverType);
        }
        throw asserter.exception("Unknown driver: " + driverType);
    }
    public void close() { webDriver.close(); }
}
