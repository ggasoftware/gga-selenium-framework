package com.ggasoftware.uitest.utils.settings;

import com.ggasoftware.uitest.apiAccessors.selenium.SeleniumDriverFactory;
import com.ggasoftware.uitest.asserter.*;
import com.ggasoftware.uitest.logger.*;
import com.ggasoftware.uitest.testRunner.*;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class FrameworkSettings {
    public static String frameworkName = "Jedi Viqa";

    public static ILogger logger = new GGALogger();
    public static IAsserter asserter = new TestNGAsserter();
    public static ITestRunner testRunner = new TestNGRunner();

    public static boolean isDemoMode = false;
    public static HighlightSettings highlightSettings = new HighlightSettings();

    public static SeleniumDriverFactory seleniumFactory = new SeleniumDriverFactory();
    public static TimeoutSettings timeouts = new TimeoutSettings();
}
