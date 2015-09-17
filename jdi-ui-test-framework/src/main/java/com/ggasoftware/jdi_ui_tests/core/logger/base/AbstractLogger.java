package com.ggasoftware.jdi_ui_tests.core.logger.base;

import com.ggasoftware.jdi_ui_tests.core.logger.enums.BusinessInfoTypes;
import com.ggasoftware.jdi_ui_tests.core.logger.enums.LogInfoTypes;
import com.ggasoftware.jdi_ui_tests.core.logger.enums.LogLevels;
import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;

import static com.ggasoftware.jdi_ui_tests.core.logger.enums.BusinessInfoTypes.*;
import static com.ggasoftware.jdi_ui_tests.core.logger.enums.LogInfoTypes.*;
import static com.ggasoftware.jdi_ui_tests.core.logger.enums.LogLevels.*;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.last;
import static java.lang.String.format;
import static java.lang.Thread.currentThread;
import static java.util.Arrays.asList;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public abstract class AbstractLogger implements ILogger {
    public void init(String message, Object... args) {
        if (logSettings.logLevel.equalOrLessThan(FATAL)
                && isMatchLogInfoType(BUSINESS) && !duplicated(message, getLineId()))
            inLog(format(message, wrap(args)), INIT);
    }

    public void suit(String message, Object... args) {
        if (logSettings.logLevel.equalOrLessThan(FATAL)
                && isMatchLogInfoType(BUSINESS) && !duplicated(message, getLineId())) {
            inLog(format(message, wrap(args)), SUIT);
        }
    }

    public void test(String message, Object... args) {
        if (logSettings.logLevel.equalOrLessThan(FATAL)
                && isMatchLogInfoType(BUSINESS) && !duplicated(message, getLineId())) {
            inLog(format(message, wrap(args)), TEST);
        }
    }

    public void step(String message, Object... args) {
        if (logSettings.logLevel.equalOrLessThan(FATAL)
                && isMatchLogInfoType(BUSINESS) && !duplicated(message, getLineId())) {
            inLog(format(message, wrap(args)), STEP);
        }
    }

    public void fatal(String message, Object... args) {
        if (logSettings.logLevel.equalOrLessThan(FATAL)
                && isMatchLogInfoType(BUSINESS) && !duplicated(message, getLineId())) {
            inLog(format(message, wrap(args)), FATAL, TECHNICAL);
        }
    }

    public void error(LogInfoTypes logInfoType, String message, Object... args) {
        if (logSettings.logLevel.equalOrLessThan(ERROR)
                && isMatchLogInfoType(logInfoType) && !duplicated(message, getLineId()))
            inLog(format(message, wrap(args)), ERROR, logInfoType);
    }

    public void warning(LogInfoTypes logInfoType, String message, Object... args) {
        if (logSettings.logLevel.equalOrLessThan(WARNING)
                && isMatchLogInfoType(logInfoType) && !duplicated(message, getLineId()))
            inLog(format(message, wrap(args)), WARNING, logInfoType);
    }

    public void info(String message, Object... args) {
        if (logSettings.logLevel.equalOrLessThan(INFO)
                && isMatchLogInfoType(FRAMEWORK) && !duplicated(message, getLineId()))
            inLog(format(message, wrap(args)), INFO, FRAMEWORK);
    }

    public void debug(String message, Object... args) {
        if (logSettings.logLevel.equalOrLessThan(DEBUG)
                && isMatchLogInfoType(TECHNICAL) && !duplicated(message, getLineId()))
            inLog(format(message, wrap(args)), DEBUG, TECHNICAL);
    }

    private String getLineId() {
        StackTraceElement stackTraceLine = last(currentThread().getStackTrace(), el -> el.getClassName().contains("core.logger"));
        return stackTraceLine.getLineNumber() + ":" + stackTraceLine.getClassName();
    }
    private boolean duplicated(String message, String lineId) {
        if (!preventDuplicates) return false;
        if (messagesMap.keys().contains(lineId) && messagesMap.get(lineId).equals(message))
            return true;
        messagesMap.addOrReplace(lineId, message);
        return false;
    }

    private boolean preventDuplicates = true;
    public ILogger forbidDuplicatedLines() {
        preventDuplicates = false;
        return this;
    }
    private MapArray<String, String> messagesMap = new MapArray<>();

    public final void toLog(String message, LogSettings settings, Object... args) {
        switch (settings.logLevel) {
            case FATAL: fatal(message, args); break;
            case ERROR: error(settings.logInfoType, message, args); break;
            case WARNING: warning(settings.logInfoType, message, args); break;
            case INFO: info(message, args); break;
            case DEBUG: debug(message, args); break;
        }
    }

    private Object[] wrap(Object[] args) { return args.length == 0 ? null : args; }

    protected void inLog(String message, LogLevels logLevel, LogInfoTypes logInfoType) {}
    protected void inLog(String message, BusinessInfoTypes infoType) {
        inLog(message, FATAL, BUSINESS);
    }

    public AbstractLogger() { this(INFO); }
    public AbstractLogger(LogLevels logLevel) {
        this(new LogSettings(logLevel));
    }
    public AbstractLogger(LogSettings logSettings) {
        this.logSettings = logSettings;
    }

    private LogSettings logSettings;
    public LogLevels getLogLevel() {
        return logSettings.logLevel;
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
