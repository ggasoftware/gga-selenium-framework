/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty ofMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */
 
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
