package com.ggasoftware.uitest.logger;

import com.ggasoftware.uitest.logger.enums.BusinessInfoTypes;
import com.ggasoftware.uitest.logger.enums.LogInfoTypes;
import com.ggasoftware.uitest.logger.enums.LogLevels;

import static com.ggasoftware.uitest.logger.enums.LogInfoTypes.BUSINESS;
import static com.ggasoftware.uitest.utils.Timer.nowTime;
import static java.lang.String.format;
import static org.testng.Reporter.log;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class TestNGLogger extends AbstractLogger {
    @Override
    public void inLog(String message, LogLevels logLevel, LogInfoTypes logInfoType) {
        log(format("%s %s~ %s", logInfoType.num, nowTime(), message));
    }
    @Override
    public void inLog(String message, BusinessInfoTypes infoType) {
        log(format("%s %s~ %s", BUSINESS.num, nowTime(), format("[%s] %s", infoType, message)));
    }

    public TestNGLogger() { super(); }
    public TestNGLogger(LogLevels logLevel) { super(logLevel); }
}
