package com.ggasoftware.jdiuitests.core.settings;

import com.ggasoftware.jdiuitests.core.asserter.BaseChecker;
import com.ggasoftware.jdiuitests.core.asserter.IAsserter;
import com.ggasoftware.jdiuitests.core.logger.ListLogger;
import com.ggasoftware.jdiuitests.core.logger.base.ILogger;
import com.ggasoftware.jdiuitests.core.testRunner.ITestRunner;
import com.ggasoftware.jdiuitests.implementation.log4j.logger.Log4JLogger;
import com.ggasoftware.jdiuitests.implementation.selenium.driver.DriverTypes;
import com.ggasoftware.jdiuitests.implementation.selenium.driver.SeleniumDriverFactory;
import com.ggasoftware.jdiuitests.implementation.testng.asserter.Check;
import com.ggasoftware.jdiuitests.implementation.testng.logger.TestNGLogger;
import com.ggasoftware.jdiuitests.implementation.testng.testRunner.TestNGRunner;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static com.ggasoftware.jdiuitests.core.asserter.DoScreen.SCREEN_ON_FAIL;
import static com.ggasoftware.jdiuitests.core.utils.common.PropertyReader.fillAction;
import static com.ggasoftware.jdiuitests.core.utils.common.PropertyReader.getProperties;
import static java.lang.Integer.parseInt;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class JDISettings {
    public static ILogger logger = new ListLogger(new TestNGLogger(), new Log4JLogger());
    public static IAsserter asserter = new Check().doScreenshot(SCREEN_ON_FAIL);
    public static ITestRunner testRunner = new TestNGRunner();
    public static SeleniumDriverFactory driverFactory = new SeleniumDriverFactory();
    public static TimeoutSettings timeouts = new TimeoutSettings();
    public static boolean isDemoMode = false;
    public static HighlightSettings highlightSettings = new HighlightSettings();
    public static boolean shortLogMessagesFormat = true;

    public static void useDriver(WebDriver driver) { driverFactory.registerDriver(() -> driver); }
    public static void useDriver(DriverTypes driver)  { driverFactory.registerDriver(driver); }
    public static void useDriver(String driverName)  { driverFactory.registerDriver(driverName); }

    public static String jdiSettingsPath = "test.properties";
    public static void initJDIFromProperties() throws Exception {
        getProperties(jdiSettingsPath);
        fillAction(driverFactory::registerDriver,   "driver");
        fillAction(driverFactory::setRunType,       "run.type");
        fillAction(p -> domain = p,                 "domain");
        fillAction(p -> timeouts.waitElementSec = parseInt(p), "timeout.wait.element");
        fillAction(p -> timeouts.waitPageLoadSec = parseInt(p), "timeout.wait.pageLoad");
        BaseChecker.defaultDoScreenType = SCREEN_ON_FAIL;
    }

    public static void initJDIFromProperties(String propertyPath) throws Exception{
        jdiSettingsPath = propertyPath;
        initJDIFromProperties();
    }
    public static String domain;
    public static boolean hasDomain() {
        return domain != null && domain.contains("://");
    }
    public static WebDriver getDriver() { return driverFactory.getDriver(); }
    
    public static JavascriptExecutor getJSExecutor() {
    	if(driverFactory.getDriver() instanceof JavascriptExecutor){
    		return (JavascriptExecutor)driverFactory.getDriver();
    		} else {
    			throw new ClassCastException("JavaScript doesn't support.");
    			}
    	}

    public static void newTest() { exceptionThrown = false; }
    public static boolean exceptionThrown = false;
    public static RuntimeException exception(String msg, Object... args) {
        exceptionThrown = true;
        return asserter.exception(msg, args);
    }
}
