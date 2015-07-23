package com.ggasoftware.uitest.control.base.logger;

import com.ggasoftware.uitest.control.base.logger.enums.BusinessInfoTypes;
import com.ggasoftware.uitest.control.base.logger.enums.LogInfoTypes;
import com.ggasoftware.uitest.control.base.logger.enums.LogLevels;

import static com.ggasoftware.uitest.utils.Timer.nowTime;
import static java.lang.String.format;
import static java.lang.System.out;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class ConsoleLogger extends AbstractLogger {
    @Override
    public void inLog(String message, LogLevels logLevel, LogInfoTypes logInfoType) {
        out.println(format("%s [%s-%s]: %s ", nowTime(), logInfoType, logLevel, message));
    }
    @Override
    public void inLog(String message, BusinessInfoTypes infoType) {
        out.println(format("%s [%s]: %s ", nowTime(), infoType, message));
    }

    public ConsoleLogger() { super(); }
    public ConsoleLogger(LogLevels logLevel) { super(logLevel); }
}
