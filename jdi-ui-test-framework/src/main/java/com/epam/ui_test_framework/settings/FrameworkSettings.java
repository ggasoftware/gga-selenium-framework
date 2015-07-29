package com.epam.ui_test_framework.settings;

import com.epam.ui_test_framework.apiAccessors.selenium.SeleniumDriverFactory;
import com.epam.ui_test_framework.asserter.IAsserter;
import com.epam.ui_test_framework.asserter.TestNGAsserter;
import com.epam.ui_test_framework.logger.Log4JLogger;
import com.epam.ui_test_framework.logger.TestNGLogger;
import com.epam.ui_test_framework.logger.base.ILogger;
import com.epam.ui_test_framework.logger.ListLogger;
import com.epam.ui_test_framework.testRunner.ITestRunner;
import com.epam.ui_test_framework.testRunner.TestNGRunner;
import com.epam.ui_test_framework.utils.linqInterfaces.JFuncTT;
import org.openqa.selenium.WebElement;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class FrameworkSettings {
    public static ILogger logger = new ListLogger(new TestNGLogger(), new Log4JLogger());
    public static IAsserter asserter = new TestNGAsserter(true);
    public static ITestRunner testRunner = new TestNGRunner();
    public static SeleniumDriverFactory driverFactory = new SeleniumDriverFactory();

    public static TimeoutSettings timeouts = new TimeoutSettings();
}
