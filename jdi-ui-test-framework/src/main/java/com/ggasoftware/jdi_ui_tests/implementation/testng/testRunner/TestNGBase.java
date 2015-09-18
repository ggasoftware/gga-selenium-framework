package com.ggasoftware.jdi_ui_tests.implementation.testng.testRunner;

import com.ggasoftware.jdi_ui_tests.core.utils.common.Timer;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.*;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.StringUtils.LineBreak;
import static com.ggasoftware.jdi_ui_tests.core.utils.usefulUtils.WebDriverUtils.killAllRunWebDrivers;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.driver.DriverTypes.CHROME;

/**
 * Created by Roman_Iovlev on 9/3/2015.
 */
public class TestNGBase {

    public static Timer timer;
    @BeforeSuite(alwaysRun = true)
    public static void setUp() throws Exception {
        logger.init("Init test run");
        killAllRunWebDrivers();
        useDriver(CHROME);
        initJDIFromProperties();
        timer = new Timer();
        logger.init("Run Tests");
    }

    @AfterSuite(alwaysRun = true)
    public static void tearDown() {
        logger.info("Test run finished. " + LineBreak + "Tests time spent: " +
                new SimpleDateFormat("HH:mm:ss.S").format(new Date(21 * 3600000 + timer.timePassedInMSec())));
        killAllRunWebDrivers();
    }
}
