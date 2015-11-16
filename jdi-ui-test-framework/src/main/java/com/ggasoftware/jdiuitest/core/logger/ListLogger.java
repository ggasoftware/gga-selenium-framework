package com.ggasoftware.jdiuitest.core.logger;

import com.ggasoftware.jdiuitest.core.logger.base.ILogger;
import com.ggasoftware.jdiuitest.core.logger.base.LogSettings;
import com.ggasoftware.jdiuitest.core.logger.enums.BusinessInfoTypes;
import com.ggasoftware.jdiuitest.core.logger.enums.LogInfoTypes;
import com.ggasoftware.jdiuitest.core.logger.enums.LogLevels;

/**
 * Created by Roman_Iovlev on 7/27/2015.
 */
public class ListLogger implements ILogger {
    private ILogger[] loggers;

    public ListLogger(ILogger... loggers) {
        this(LogLevels.INFO, loggers);
    }

    public ListLogger(LogLevels logLevels, ILogger... loggers) {
        this.loggers = loggers;
        setLogLevels(logLevels);
    }

    public void setLogLevels(LogLevels logLevels) {
        for (ILogger logger : loggers)
            logger.setLogLevels(logLevels);
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
    public void inLog(String message, LogLevels logLevel, LogInfoTypes logInfoType) {
        for (ILogger logger : loggers)
            logger.inLog(message, logLevel, logInfoType);
    }

    public void inLog(String message, BusinessInfoTypes infoType) {
        for (ILogger logger : loggers)
            logger.inLog(message, infoType);
    }
}
