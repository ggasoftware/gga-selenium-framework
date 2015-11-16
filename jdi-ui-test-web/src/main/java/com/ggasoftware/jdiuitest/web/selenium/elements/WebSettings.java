package com.ggasoftware.jdiuitest.web.selenium.elements;

import com.ggasoftware.jdiuitest.core.logger.ListLogger;
import com.ggasoftware.jdiuitest.core.settings.JDISettings;
import com.ggasoftware.jdiuitest.web.logger.Log4JLogger;
import com.ggasoftware.jdiuitest.web.selenium.driver.DriverTypes;
import com.ggasoftware.jdiuitest.web.selenium.driver.SeleniumDriverFactory;
import com.ggasoftware.jdiuitest.web.testng.asserter.Check;
import com.ggasoftware.jdiuitest.web.testng.logger.TestNGLogger;
import com.ggasoftware.jdiuitest.web.testng.testRunner.TestNGRunner;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static com.ggasoftware.jdiuitest.core.asserter.DoScreen.SCREEN_ON_FAIL;

/**
 * Created by Roman_Iovlev on 11/13/2015.
 */
public class WebSettings extends JDISettings {
    public static WebDriver getDriver() { return getDriverFactory().getDriver(); }
    public static SeleniumDriverFactory getDriverFactory() { return (SeleniumDriverFactory)JDISettings.driverFactory; }

    public static void useDriver(DriverTypes driverName) {
        getDriverFactory().registerDriver(driverName);
    }
    public static void useDriver(WebDriver driver) {
        getDriverFactory().registerDriver(() -> driver);
    }
    public static JavascriptExecutor getJSExecutor() {
        if (driverFactory.getDriver() instanceof JavascriptExecutor) {
            return (JavascriptExecutor) driverFactory.getDriver();
        } else {
            throw new ClassCastException("JavaScript doesn't support.");
        }
    }

    public static void init() {
        driverFactory = new SeleniumDriverFactory();
        asserter = new Check().doScreenshot(SCREEN_ON_FAIL);
        logger = new ListLogger(new TestNGLogger(), new Log4JLogger());
        testRunner = new TestNGRunner();
    }
}
