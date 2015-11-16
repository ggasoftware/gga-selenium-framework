package com.ggasoftware.jdiuitest.core.logger;

import com.ggasoftware.jdiuitest.core.logger.base.AbstractLogger;
import com.ggasoftware.jdiuitest.core.logger.enums.BusinessInfoTypes;
import com.ggasoftware.jdiuitest.core.logger.enums.LogInfoTypes;
import com.ggasoftware.jdiuitest.core.logger.enums.LogLevels;
import com.ggasoftware.jdiuitest.core.utils.common.Timer;

import static java.lang.String.format;
import static java.lang.System.out;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class ConsoleLogger extends AbstractLogger {
    public ConsoleLogger() {
        super();
    }

    public ConsoleLogger(LogLevels logLevel) {
        super(logLevel);
    }

    public void inLog(String message, LogLevels logLevel, LogInfoTypes logInfoType) {
        out.println(format("%s [%s-%s]: %s ", Timer.nowTime(), logInfoType, logLevel, message));
    }

    public void inLog(String message, BusinessInfoTypes infoType) {
        out.println(format("%s [%s]: %s ", Timer.nowTime(), infoType, message));
    }
}
