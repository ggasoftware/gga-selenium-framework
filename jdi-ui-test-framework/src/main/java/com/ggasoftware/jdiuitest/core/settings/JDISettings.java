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
package com.ggasoftware.jdiuitest.core.settings;

import com.ggasoftware.jdiuitest.core.interfaces.settings.IAsserter;
import com.ggasoftware.jdiuitest.core.interfaces.settings.IDriver;
import com.ggasoftware.jdiuitest.core.interfaces.settings.ITestRunner;
import com.ggasoftware.jdiuitest.core.logger.base.ILogger;

import java.io.IOException;

import static com.ggasoftware.jdiuitest.core.asserter.BaseChecker.setDefaultDoScreenType;
import static com.ggasoftware.jdiuitest.core.asserter.DoScreen.SCREEN_ON_FAIL;
import static com.ggasoftware.jdiuitest.core.utils.common.PropertyReader.fillAction;
import static com.ggasoftware.jdiuitest.core.utils.common.PropertyReader.getProperties;
import static java.lang.Integer.parseInt;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public abstract class JDISettings {
    protected JDISettings() { }

    public static ILogger logger;
    public static IAsserter asserter;
    public static ITestRunner testRunner;
    public static TimeoutSettings timeouts = new TimeoutSettings();
    public static boolean isDemoMode;
    public static HighlightSettings highlightSettings = new HighlightSettings();
    public static boolean shortLogMessagesFormat = true;
    public static String jdiSettingsPath = "test.properties";
    public static String domain;
    public static boolean exceptionThrown;
    public static IDriver driverFactory;

    public static void useDriver(String driverName) {
        driverFactory.registerDriver(driverName);
    }

    public static void initJDIFromProperties() throws IOException {
        getProperties(jdiSettingsPath);
        fillAction(driverFactory::registerDriver, "driver");
        fillAction(driverFactory::setRunType, "run.type");
        fillAction(p -> domain = p, "domain");
        fillAction(p -> timeouts.waitElementSec = parseInt(p), "timeout.wait.element");
        fillAction(p -> timeouts.waitPageLoadSec = parseInt(p), "timeout.wait.pageLoad");
        setDefaultDoScreenType(SCREEN_ON_FAIL);
    }

    public static void initJDIFromProperties(String propertyPath) throws IOException {
        jdiSettingsPath = propertyPath;
        initJDIFromProperties();
    }

    public static boolean hasDomain() {
        return domain != null && domain.contains("://");
    }

    public static void newTest() {
        exceptionThrown = false;
    }

    public static RuntimeException exception(String msg, Object... args) {
        exceptionThrown = true;
        return asserter.exception(msg, args);
    }
}
