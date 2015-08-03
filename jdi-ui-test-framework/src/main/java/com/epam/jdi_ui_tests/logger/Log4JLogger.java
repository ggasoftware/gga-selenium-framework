package com.epam.jdi_ui_tests.logger;

import com.epam.jdi_ui_tests.logger.base.AbstractLogger;
import com.epam.jdi_ui_tests.logger.enums.LogLevels;
import com.epam.jdi_ui_tests.logger.enums.BusinessInfoTypes;
import com.epam.jdi_ui_tests.logger.enums.LogInfoTypes;
import org.apache.log4j.Logger;

import static com.epam.jdi_ui_tests.utils.common.Timer.nowTimeShort;
import static com.epam.jdi_ui_tests.settings.FrameworkData.frameworkName;
import static java.lang.String.format;
import static org.apache.log4j.LogManager.getLogger;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class Log4JLogger extends AbstractLogger {

    @Override
    public void inLog(String message, LogLevels logLevel, LogInfoTypes logInfoType) {
        switch (logLevel) {
            case FATAL: log4j.fatal(format("%s: %s", nowTimeShort(), message)); break;
            case ERROR: log4j.error(format("%s: %s", nowTimeShort(), message)); break;
            case WARNING: log4j.warn(format("%s: %s", nowTimeShort(), message)); break;
            case INFO: log4j.info(format("%s: %s", nowTimeShort(), message)); break;
            case DEBUG: log4j.debug(format("%s: %s", nowTimeShort(), message)); break;
        }
    }
    @Override
    public void inLog(String message, BusinessInfoTypes infoType) {
        log4j.info(format("[%s] %s: %s", infoType, nowTimeShort(), message));
    }

    private Logger log4j = getLogger(frameworkName + " logger");

    public Log4JLogger() { super(); }
    public Log4JLogger(LogLevels logLevel) { super(logLevel); }

}
