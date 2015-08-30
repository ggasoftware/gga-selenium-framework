package com.ggasoftware.jdi_ui_tests.settings;

import com.ggasoftware.jdi_ui_tests.apiAccessors.selenium.DriverTypes;
import com.ggasoftware.jdi_ui_tests.apiAccessors.selenium.SeleniumDriverFactory;
import com.ggasoftware.jdi_ui_tests.asserter.IAsserter;
import com.ggasoftware.jdi_ui_tests.asserter.testNG.Check;
import com.ggasoftware.jdi_ui_tests.logger.ListLogger;
import com.ggasoftware.jdi_ui_tests.logger.Log4JLogger;
import com.ggasoftware.jdi_ui_tests.logger.TestNGLogger;
import com.ggasoftware.jdi_ui_tests.logger.base.ILogger;
import com.ggasoftware.jdi_ui_tests.testRunner.ITestRunner;
import com.ggasoftware.jdi_ui_tests.testRunner.TestNGRunner;
import org.openqa.selenium.WebDriver;

import static com.ggasoftware.jdi_ui_tests.asserter.DoScreen.SCREEN_ON_FAIL;
import static com.ggasoftware.jdi_ui_tests.utils.common.PropertyReader.fillAction;
import static com.ggasoftware.jdi_ui_tests.utils.common.PropertyReader.getProperties;
import static java.lang.Integer.parseInt;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class JDISettings {
    public static ILogger logger = new ListLogger(new TestNGLogger(), new Log4JLogger());
    public static IAsserter asserter = new Check().setFailMethod(SCREEN_ON_FAIL);
    public static ITestRunner testRunner = new TestNGRunner();
    public static SeleniumDriverFactory driverFactory = new SeleniumDriverFactory();
    public static TimeoutSettings timeouts = new TimeoutSettings();

    public static void useDriver(WebDriver driver) { driverFactory.registerDriver(() -> driver); }
    public static void useDriver(DriverTypes driver)  { driverFactory.registerDriver(driver); }
    public static void useDriver(String driverName)  { driverFactory.registerDriver(driverName); }

    public static String jdiSettingsPath = "test.properties";
    public static void initJDIFromProperties() throws Exception {
        getProperties(jdiSettingsPath);
        fillAction(driverFactory::registerDriver, "driver");
        fillAction(driverFactory::setRunType, "run.type");
        fillAction(p -> domain = p, "domain");
        fillAction(p -> timeouts.waitElementSec = parseInt(p), "timeout.wait.element");
        fillAction(p -> timeouts.waitPageLoadSec = parseInt(p), "timeout.wait.pageLoad");
    }
    public static void initJDIFromProperties(String propertyPath) throws Exception {
        jdiSettingsPath = propertyPath;
        initJDIFromProperties();
    }

    public static String domain;
    public static boolean hasDomain() {
        return domain != null && domain.contains("://");
    }
}
