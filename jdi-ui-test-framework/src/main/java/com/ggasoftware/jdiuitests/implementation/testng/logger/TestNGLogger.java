package com.ggasoftware.jdiuitests.implementation.testng.logger;

import com.ggasoftware.jdiuitests.core.logger.base.AbstractLogger;
import com.ggasoftware.jdiuitests.core.logger.enums.BusinessInfoTypes;
import com.ggasoftware.jdiuitests.core.logger.enums.LogInfoTypes;
import com.ggasoftware.jdiuitests.core.logger.enums.LogLevels;
import com.ggasoftware.jdiuitests.core.utils.map.MapArray;
import com.ggasoftware.jdiuitests.core.utils.common.Timer;

import static com.ggasoftware.jdiuitests.core.logger.enums.LogInfoTypes.*;
import static java.lang.String.format;
import static org.testng.Reporter.log;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class TestNGLogger extends AbstractLogger {
    private MapArray<LogInfoTypes, Integer> typesMap = new MapArray<>(new Object[][] {
            {BUSINESS, 2},
            {FRAMEWORK, 1},
            {TECHNICAL, 0}
    });

    @Override
    public void inLog(String message, LogLevels logLevel, LogInfoTypes logInfoType) {
        log(format("%s %s~ %s", typesMap.get(logInfoType), Timer.nowTime(), message));
    }
    @Override
    public void inLog(String message, BusinessInfoTypes infoType) {
        log(format("%s %s~ %s", typesMap.get(BUSINESS), Timer.nowTime(), format("[%s] %s", infoType, message)));
    }

    public TestNGLogger() { super(); }
    public TestNGLogger(LogLevels logLevel) { super(logLevel); }
}
