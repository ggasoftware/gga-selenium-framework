package com.epam.ui_test_framework.settings;

import com.epam.ui_test_framework.apiAccessors.selenium.SeleniumDriverFactory;
import com.epam.ui_test_framework.asserter.IAsserter;
import com.epam.ui_test_framework.asserter.TestNGScreenshotAsserter;
import com.epam.ui_test_framework.logger.TestNGLog4JLogger;
import com.epam.ui_test_framework.logger.ILogger;
import com.epam.ui_test_framework.testRunner.ITestRunner;
import com.epam.ui_test_framework.testRunner.TestNGRunner;
import com.epam.ui_test_framework.utils.linqInterfaces.JFuncTT;
import org.openqa.selenium.WebElement;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class FrameworkSettings {
    public static String frameworkName = "Jedi Viqa";

    public static ILogger logger = new TestNGLog4JLogger();
    public static IAsserter asserter = new TestNGScreenshotAsserter();
    public static ITestRunner testRunner = new TestNGRunner();
    public static SeleniumDriverFactory seleniumFactory = new SeleniumDriverFactory();

    public static boolean isDemoMode = false;
    public static HighlightSettings highlightSettings = new HighlightSettings();

    public static TimeoutSettings timeouts = new TimeoutSettings();
    public static JFuncTT<WebElement, Boolean> elementSearchCriteria = WebElement::isDisplayed;
    public static String applicationVersion;

    // TODO need to remove
    public static String testName;
}
