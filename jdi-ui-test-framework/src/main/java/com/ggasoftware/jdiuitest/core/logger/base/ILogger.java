package com.ggasoftware.jdiuitest.core.logger.base;

import com.ggasoftware.jdiuitest.core.logger.enums.BusinessInfoTypes;
import com.ggasoftware.jdiuitest.core.logger.enums.LogInfoTypes;
import com.ggasoftware.jdiuitest.core.logger.enums.LogLevels;


/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public interface ILogger {
    void init(String message, Object... args);

    void suit(String message, Object... args);

    void test(String message, Object... args);

    void step(String message, Object... args);

    void fatal(String message, Object... args);

    void error(LogInfoTypes logInfoType, String message, Object... args);

    void warning(LogInfoTypes logInfoType, String message, Object... args);

    void info(String message, Object... args);

    void debug(String message, Object... args);

    void toLog(String message, LogSettings settings, Object... args);

    LogLevels getLogLevel();

    void setLogLevels(LogLevels logLevels);
    void inLog(String message, LogLevels logLevel, LogInfoTypes logInfoType);
    void inLog(String message, BusinessInfoTypes infoType);
}
