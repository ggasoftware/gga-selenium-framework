package com.epam.jdi_ui_tests.settings;

import com.epam.jdi_ui_tests.apiAccessors.selenium.SeleniumDriverFactory;
import com.epam.jdi_ui_tests.asserter.IAsserter;
import com.epam.jdi_ui_tests.asserter.TestNGAsserter;
import com.epam.jdi_ui_tests.logger.Log4JLogger;
import com.epam.jdi_ui_tests.logger.TestNGLogger;
import com.epam.jdi_ui_tests.logger.base.ILogger;
import com.epam.jdi_ui_tests.logger.ListLogger;
import com.epam.jdi_ui_tests.testRunner.ITestRunner;
import com.epam.jdi_ui_tests.testRunner.TestNGRunner;
import org.openqa.selenium.WebDriver;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class FrameworkSettings {
    public static ILogger logger = new ListLogger(new TestNGLogger(), new Log4JLogger());
    public static IAsserter asserter = new TestNGAsserter(true);
    public static ITestRunner testRunner = new TestNGRunner();
    public static SeleniumDriverFactory driverFactory = new SeleniumDriverFactory();

    public static TimeoutSettings timeouts = new TimeoutSettings();

    public static void useDriver(WebDriver driver) { driverFactory.registerDriver(driver); }
    public static void useDriver(Drivers driver) { driverFactory.registerDriver(driver); }

}
