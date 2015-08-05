package com.ggasoftware.jdi_ui_tests.logger.base;

import com.ggasoftware.jdi_ui_tests.logger.enums.LogLevels;
import com.ggasoftware.jdi_ui_tests.logger.enums.LogInfoTypes;


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
}
