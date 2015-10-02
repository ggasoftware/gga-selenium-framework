package com.ggasoftware.jdi_ui_tests.implementation.log4j.logger;

import com.ggasoftware.jdi_ui_tests.core.logger.base.AbstractLogger;
import com.ggasoftware.jdi_ui_tests.core.logger.enums.BusinessInfoTypes;
import com.ggasoftware.jdi_ui_tests.core.logger.enums.LogInfoTypes;
import com.ggasoftware.jdi_ui_tests.core.logger.enums.LogLevels;
import com.ggasoftware.jdi_ui_tests.core.utils.common.Timer;
import org.apache.log4j.Logger;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDIData.frameworkName;
import static org.apache.log4j.LogManager.getLogger;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class Log4JLogger extends AbstractLogger {

    @Override
    public void inLog(String message, LogLevels logLevel, LogInfoTypes logInfoType) {
        switch (logLevel) {
            case FATAL: log4j.fatal(String.format("%s: %s", Timer.nowTimeShort(), message)); break;
            case ERROR: log4j.error(String.format("%s: %s", Timer.nowTimeShort(), message)); break;
            case WARNING: log4j.warn(String.format("%s: %s", Timer.nowTimeShort(), message)); break;
            case INFO: log4j.info(String.format("%s: %s", Timer.nowTimeShort(), message)); break;
            case DEBUG: log4j.debug(String.format("%s: %s", Timer.nowTimeShort(), message)); break;
        }
    }
    @Override
    public void inLog(String message, BusinessInfoTypes infoType) {
        log4j.info(String.format("[%s] %s: %s", infoType, Timer.nowTimeShort(), message));
    }

    private Logger log4j = getLogger("log4j " + frameworkName + " logger");

    public Log4JLogger() { super(); }
    public Log4JLogger(LogLevels logLevel) { super(logLevel); }

}
