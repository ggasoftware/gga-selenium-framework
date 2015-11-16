package com.ggasoftware.jdiuitest.web.logger;

import com.ggasoftware.jdiuitest.core.logger.base.AbstractLogger;
import com.ggasoftware.jdiuitest.core.logger.enums.BusinessInfoTypes;
import com.ggasoftware.jdiuitest.core.logger.enums.LogInfoTypes;
import com.ggasoftware.jdiuitest.core.logger.enums.LogLevels;
import com.ggasoftware.jdiuitest.core.settings.JDIData;
import com.ggasoftware.jdiuitest.core.utils.common.Timer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class Log4JLogger extends AbstractLogger {

    private Logger log4j = LogManager.getLogger("log4j " + JDIData.FRAMEWORK_NAME + " logger");

    public Log4JLogger() {
        super();
    }

    public Log4JLogger(LogLevels logLevel) {
        super(logLevel);
    }

    public void inLog(String message, LogLevels logLevel, LogInfoTypes logInfoType) {
        switch (logLevel) {
            case FATAL:
                log4j.fatal(String.format("%s: %s", Timer.nowTimeShort(), message));
                break;
            case ERROR:
                log4j.error(String.format("%s: %s", Timer.nowTimeShort(), message));
                break;
            case WARNING:
                log4j.warn(String.format("%s: %s", Timer.nowTimeShort(), message));
                break;
            case INFO:
                log4j.info(String.format("%s: %s", Timer.nowTimeShort(), message));
                break;
            case DEBUG:
                log4j.debug(String.format("%s: %s", Timer.nowTimeShort(), message));
                break;
        }
    }

    public void inLog(String message, BusinessInfoTypes infoType) {
        log4j.info(String.format("[%s] %s: %s", infoType, Timer.nowTimeShort(), message));
    }

}
