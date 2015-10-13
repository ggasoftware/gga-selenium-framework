package com.ggasoftware.jdiuitests.implementation.testng.testRunner;

import com.ggasoftware.jdiuitests.core.settings.JDISettings;
import com.ggasoftware.jdiuitests.core.utils.common.Timer;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ggasoftware.jdiuitests.core.settings.JDISettings.*;
import static com.ggasoftware.jdiuitests.core.utils.common.StringUtils.LineBreak;
import static com.ggasoftware.jdiuitests.core.utils.usefulUtils.WebDriverUtils.killAllRunWebDrivers;
import static com.ggasoftware.jdiuitests.implementation.selenium.driver.DriverTypes.CHROME;

/**
 * Created by Roman_Iovlev on 9/3/2015.
 */
public class TestNGBase {

    protected static Timer timer;
    public static long getTestRunTime() { return timer.timePassedInMSec(); }

    @BeforeSuite(alwaysRun = true)
    public static void jdiSetUp() throws Exception {
        logger.init("Init test run");
        killAllRunWebDrivers();
        initJDIFromProperties();
        if (!JDISettings.driverFactory.hasDrivers())
            useDriver(CHROME);
        timer = new Timer();
    }

    @AfterSuite(alwaysRun = true)
    public static void jdiTearDown() {
        logger.info("Test run finished. " + LineBreak + "Total test run time: " +
            new SimpleDateFormat("HH:mm:ss.S").format(new Date(21 * 3600000 + getTestRunTime())));
        killAllRunWebDrivers();
    }
}
