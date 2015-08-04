package com.epam.jdi_ui_tests.settings;

import com.epam.jdi_ui_tests.apiAccessors.selenium.DriverTypes;
import com.epam.jdi_ui_tests.apiAccessors.selenium.SeleniumDriverFactory;
import com.epam.jdi_ui_tests.asserter.*;
import com.epam.jdi_ui_tests.logger.*;
import com.epam.jdi_ui_tests.logger.base.ILogger;
import com.epam.jdi_ui_tests.testRunner.*;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

import static com.epam.jdi_ui_tests.utils.common.PropertyReader.*;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class JDISettings {
    public static ILogger logger = new ListLogger(new TestNGLogger(), new Log4JLogger());
    public static IAsserter asserter = new TestNGAsserter(true);
    public static ITestRunner testRunner = new TestNGRunner();
    public static SeleniumDriverFactory driverFactory = new SeleniumDriverFactory();

    public static TimeoutSettings timeouts = new TimeoutSettings();

    public static void useDriver(WebDriver driver) { driverFactory.registerDriver(() -> driver); }
    public static void useDriver(DriverTypes driver)  { driverFactory.registerDriver(driver); }

    public static String jdiSettingsPath = "test.properties";
    public static void initJDIFromProperties() throws Exception {
        getProperties(jdiSettingsPath);
        fillAction(driverFactory::registerDriver, "driver");
        fillAction(driverFactory::setRunType, "run.type");
        fillAction(p -> domain = p, "domain");
    }
    public static void initJDIFromProperties(String propertyPath) throws Exception {
        jdiSettingsPath = propertyPath;
        initJDIFromProperties();
    }

    public static String domain;
    public static boolean hasDomain() {
        return domain != null && !domain.contains("://");
    }
}
