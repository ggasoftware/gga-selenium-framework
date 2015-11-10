package com.ggasoftware.jdiuitests.core.logger.base;

import com.ggasoftware.jdiuitests.core.logger.enums.LogInfoTypes;
import com.ggasoftware.jdiuitests.core.logger.enums.LogLevels;
import com.ggasoftware.jdiuitests.core.utils.map.MapArray;

import static com.ggasoftware.jdiuitests.core.logger.enums.BusinessInfoTypes.*;
import static com.ggasoftware.jdiuitests.core.logger.enums.LogInfoTypes.*;
import static com.ggasoftware.jdiuitests.core.logger.enums.LogLevels.*;
import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import static java.util.Arrays.asList;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public abstract class AbstractLogger implements ILogger {
    private boolean preventDuplicates = true;
    private MapArray<String, String> messagesMap = new MapArray<>();
    private LogSettings logSettings;

    public AbstractLogger() {
        this(INFO);
    }
    public void setLogLevels(LogLevels logLevels) {
        logSettings.logLevels = logLevels;
    }

    public AbstractLogger(LogLevels logLevel) {
        this(new LogSettings(logLevel));
    }

    public AbstractLogger(LogSettings logSettings) {
        this.logSettings = logSettings;
    }

    public void init(String message, Object... args) {
        if (logSettings.logLevels.equalOrLessThan(FATAL)
                && isMatchLogInfoType(BUSINESS) && !duplicated(message, getLineId()))
            inLog(format(message, wrap(args)), INIT);
    }

    public void suit(String message, Object... args) {
        if (logSettings.logLevels.equalOrLessThan(FATAL)
                && isMatchLogInfoType(BUSINESS) && !duplicated(message, getLineId())) {
            inLog(format(message, wrap(args)), SUIT);
        }
    }

    public void test(String message, Object... args) {
        if (logSettings.logLevels.equalOrLessThan(FATAL)
                && isMatchLogInfoType(BUSINESS) && !duplicated(message, getLineId())) {
            inLog(format(message, wrap(args)), TEST);
        }
    }

    public void step(String message, Object... args) {
        if (logSettings.logLevels.equalOrLessThan(FATAL)
                && isMatchLogInfoType(BUSINESS) && !duplicated(message, getLineId())) {
            inLog(format(message, wrap(args)), STEP);
        }
    }

    public void fatal(String message, Object... args) {
        if (logSettings.logLevels.equalOrLessThan(FATAL)
                && isMatchLogInfoType(BUSINESS) && !duplicated(message, getLineId())) {
            inLog(format(message, wrap(args)), FATAL, TECHNICAL);
        }
    }

    public void error(LogInfoTypes logInfoType, String message, Object... args) {
        if (logSettings.logLevels.equalOrLessThan(ERROR)
                && isMatchLogInfoType(logInfoType) && !duplicated(message, getLineId()))
            inLog(format(message, wrap(args)), ERROR, logInfoType);
    }

    public void warning(LogInfoTypes logInfoType, String message, Object... args) {
        if (logSettings.logLevels.equalOrLessThan(WARNING)
                && isMatchLogInfoType(logInfoType) && !duplicated(message, getLineId()))
            inLog(format(message, wrap(args)), WARNING, logInfoType);
    }

    public void info(String message, Object... args) {
        if (logSettings.logLevels.equalOrLessThan(INFO)
                && isMatchLogInfoType(FRAMEWORK) && !duplicated(message, getLineId()))
            inLog(format(message, wrap(args)), INFO, FRAMEWORK);
    }

    public void debug(String message, Object... args) {
        if (logSettings.logLevels.equalOrLessThan(DEBUG)
                && isMatchLogInfoType(TECHNICAL) && !duplicated(message, getLineId()))
            inLog(format(message, wrap(args)), DEBUG, TECHNICAL);
    }

    private String getLineId() {
        StackTraceElement stackTraceLine = null;
        for (StackTraceElement line : currentThread().getStackTrace())
            if (line.getClassName().contains("com.ggasoftware.jdiuitests"))
                stackTraceLine = null;
            else if (stackTraceLine == null)
                stackTraceLine = line;
        return stackTraceLine != null ? stackTraceLine.getLineNumber() + ":" + stackTraceLine.getClassName() : null;
    }

    private boolean duplicated(String message, String lineId) {
        if (!preventDuplicates)
            return false;
        if (messagesMap.keys().contains(lineId) && messagesMap.get(lineId).equals(message))
            return true;
        messagesMap.addOrReplace(lineId, message);
        return false;
    }

    public ILogger forbidDuplicatedLines() {
        preventDuplicates = false;
        return this;
    }

    public final void toLog(String message, LogSettings settings, Object... args) {
        switch (settings.logLevels) {
            case FATAL:
                fatal(message, args);
                break;
            case ERROR:
                error(settings.logInfoType, message, args);
                break;
            case WARNING:
                warning(settings.logInfoType, message, args);
                break;
            case INFO:
                info(message, args);
                break;
            case DEBUG:
                debug(message, args);
                break;
            default: error(TECHNICAL, "Unknown logging type: " + settings.logLevels.toString());
        }
    }

    private Object[] wrap(Object[] args) {
        return args.length == 0 ? null : args;
    }

    public LogLevels getLogLevel() {
        return logSettings.logLevels;
    }

    private boolean isMatchLogInfoType(LogInfoTypes logInfoType) {
        switch (logInfoType) {
            case BUSINESS:
                return asList(new Integer[]{1, 3, 5, 7}).contains(logSettings.logInfoTypes);
            case FRAMEWORK:
                return asList(new Integer[]{2, 3, 6, 7}).contains(logSettings.logInfoTypes);
            case TECHNICAL:
                return asList(new Integer[]{4, 5, 6, 7}).contains(logSettings.logInfoTypes);
            default:
                return false;
        }
    }
}
