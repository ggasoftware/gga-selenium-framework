package com.ggasoftware.jdi_ui_tests.core.logger;

import com.ggasoftware.jdi_ui_tests.core.logger.enums.LogInfoTypes;
import com.ggasoftware.jdi_ui_tests.core.logger.enums.LogLevels;

/**
 * Created by Roman_Iovlev on 7/27/2015.
 */
public class ListLogger implements ILogger {
    private ILogger[] loggers;
    public ListLogger(ILogger... loggers) {
        this(LogLevels.INFO, loggers);
    }
    public ListLogger(LogLevels logLevel, ILogger... loggers) {
        this.loggers = loggers;
    }

    public void init(String message, Object... args) {
        for (ILogger logger : loggers)
            logger.init(message, args);
    }

    public void suit(String message, Object... args) {
        for (ILogger logger : loggers)
            logger.suit(message, args);
    }

    public void test(String message, Object... args) {
        for (ILogger logger : loggers)
            logger.test(message, args);
    }

    public void step(String message, Object... args) {
        for (ILogger logger : loggers)
            logger.step(message, args);
    }

    public void fatal(String message, Object... args) {
        for (ILogger logger : loggers)
            logger.fatal(message, args);
    }

    public void error(LogInfoTypes logInfoType, String message, Object... args) {
        for (ILogger logger : loggers)
            logger.error(logInfoType, message, args);
    }

    public void warning(LogInfoTypes logInfoType, String message, Object... args) {
        for (ILogger logger : loggers)
            logger.warning(logInfoType, message, args);
    }

    public void info(String message, Object... args) {
        for (ILogger logger : loggers)
            logger.info(message, args);
    }

    public void debug(String message, Object... args) {
        for (ILogger logger : loggers)
            logger.debug(message, args);
    }

    public void toLog(String message, LogSettings settings, Object... args) {
        for (ILogger logger : loggers)
            logger.toLog(message, settings, args);
    }

    public LogLevels getLogLevel() {
        LogLevels logLevel = LogLevels.OFF;
        for (ILogger logger : loggers) {
            if (logLevel.getPriority() < logger.getLogLevel().getPriority())
                logLevel = logger.getLogLevel();
        }
        return logLevel;
    }
}
