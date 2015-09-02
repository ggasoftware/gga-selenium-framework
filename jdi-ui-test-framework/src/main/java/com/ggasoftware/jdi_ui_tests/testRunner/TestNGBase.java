package com.ggasoftware.jdi_ui_tests.testRunner;

import com.ggasoftware.jdi_ui_tests.utils.common.Timer;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ggasoftware.jdi_ui_tests.apiAccessors.selenium.DriverTypes.CHROME;
import static com.ggasoftware.jdi_ui_tests.settings.JDISettings.*;
import static com.ggasoftware.jdi_ui_tests.utils.common.StringUtils.LineBreak;
import static com.ggasoftware.jdi_ui_tests.utils.usefulUtils.WebDriverUtils.killAllRunWebDrivers;

/**
 * Created by Roman_Iovlev on 8/30/2015.
 */
public abstract class TestNGBase {

    public static Timer timer;

    @BeforeSuite(alwaysRun = true)
    public static void setUp() throws Exception {
        logger.init("Init test run");
        killAllRunWebDrivers();
        initJDIFromProperties();
        useDriver(CHROME);
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
