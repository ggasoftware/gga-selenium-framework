package com.ggasoftware.jdiuitests.implementation.testng.testRunner;

import com.ggasoftware.jdiuitests.core.settings.JDISettings;
import com.ggasoftware.jdiuitests.core.utils.WebDriverUtils;
import com.ggasoftware.jdiuitests.core.utils.common.StringUtils;
import com.ggasoftware.jdiuitests.core.utils.common.Timer;
import com.ggasoftware.jdiuitests.implementation.selenium.driver.DriverTypes;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Roman_Iovlev on 9/3/2015.
 */
public class TestNGBase {

    protected static Timer timer;

    public static long getTestRunTime() {
        return timer.timePassedInMSec();
    }

    @BeforeSuite(alwaysRun = true)
    public static void jdiSetUp() throws Exception {
        JDISettings.logger.init("Init test run");
        WebDriverUtils.killAllRunWebDrivers();
        JDISettings.initJDIFromProperties();
        if (!JDISettings.driverFactory.hasDrivers())
            JDISettings.useDriver(DriverTypes.CHROME);
        timer = new Timer();
    }

    @AfterSuite(alwaysRun = true)
    public static void jdiTearDown() {
        JDISettings.logger.info("Test run finished. " + StringUtils.LINE_BREAK + "Total test run time: " +
                new SimpleDateFormat("HH:mm:ss.S").format(new Date(21 * 3600000 + getTestRunTime())));
        WebDriverUtils.killAllRunWebDrivers();
    }
}
