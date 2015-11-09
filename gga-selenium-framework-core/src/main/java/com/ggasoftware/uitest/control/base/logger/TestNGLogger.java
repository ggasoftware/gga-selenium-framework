package com.ggasoftware.uitest.control.base.logger;

import com.ggasoftware.uitest.control.base.logger.enums.BusinessInfoTypes;
import com.ggasoftware.uitest.control.base.logger.enums.LogInfoTypes;
import com.ggasoftware.uitest.control.base.logger.enums.LogLevels;

import static com.ggasoftware.uitest.control.base.logger.enums.LogInfoTypes.BUSINESS;
import static com.ggasoftware.uitest.utils.Timer.nowTime;
import static org.testng.Reporter.log;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class TestNGLogger extends AbstractLogger {
    public TestNGLogger() {
        super();
    }

    public TestNGLogger(LogLevels logLevel) {
        super(logLevel);
    }

    @Override
    public void inLog(String message, LogLevels logLevel, LogInfoTypes logInfoType) {
        log(format("%s %s~ %s", logInfoType.num, nowTime(), message));
    }

    @Override
    public void inLog(String message, BusinessInfoTypes infoType) {
        log(format("%s %s~ %s", BUSINESS.num, nowTime(), format("[%s] %s", infoType, message)));
    }
}
