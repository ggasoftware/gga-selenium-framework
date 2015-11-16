package com.ggasoftware.jdiuitest.web.testng.testRunner;

import com.ggasoftware.jdiuitest.core.settings.JDISettings;
import com.ggasoftware.jdiuitest.core.utils.common.StringUtils;
import com.ggasoftware.jdiuitest.core.utils.common.Timer;
import com.ggasoftware.jdiuitest.web.selenium.driver.DriverTypes;
import com.ggasoftware.jdiuitest.web.selenium.elements.WebSettings;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ggasoftware.jdiuitest.web.selenium.driver.WebDriverUtils.killAllRunWebDrivers;

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
        WebSettings.init();
        JDISettings.logger.init("Init test run");
        killAllRunWebDrivers();
        JDISettings.initJDIFromProperties();
        if (!JDISettings.driverFactory.hasDrivers())
            WebSettings.useDriver(DriverTypes.CHROME);
        timer = new Timer();
    }

    @AfterSuite(alwaysRun = true)
    public static void jdiTearDown() {
        JDISettings.logger.info("Test run finished. " + StringUtils.LINE_BREAK + "Total test run time: " +
                new SimpleDateFormat("HH:mm:ss.S").format(new Date(21 * 3600000 + getTestRunTime())));
        killAllRunWebDrivers();
    }
}
