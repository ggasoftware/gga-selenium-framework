package com.ggasoftware.uitest.logger;

import com.ggasoftware.uitest.logger.enums.LogInfoTypes;
import com.ggasoftware.uitest.logger.enums.LogLevels;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public interface ILogger {
    void init(String message);
    void suit(String message);
    void test(String message);
    void step(String message);
    void fatal(String message);
    void error(String message, LogInfoTypes logInfoType);
    void warning(String message, LogInfoTypes logInfoType);
    void info(String message);
    void debug(String message);

    LogLevels getLogLevel();
}
