package com.ggasoftware.uitest.logger;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public interface ILogger {
    void init(String message);
    void suit(String message);
    void test(String message);
    void step(String message);
    void fatal(String message);
    void error(String message);
    void warning(String message);
    void info(String message);
    void debug(String message);

    LogLevels getLogLevel();
}
