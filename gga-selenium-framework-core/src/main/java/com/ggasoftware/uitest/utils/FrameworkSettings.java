package com.ggasoftware.uitest.utils;

import com.ggasoftware.uitest.asserter.*;
import com.ggasoftware.uitest.logger.*;
import com.ggasoftware.uitest.testRunner.*;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class FrameworkSettings {
    public static ILogger logger = new Log4JLogger();
    public static IAsserter asserter = new TestNGAsserter();
    public static ITestRunner testRunner = new TestNGRunner();

    public static boolean isDemoMode = false;
    public static HighlightSettings highlightSettings = new HighlightSettings();
}
