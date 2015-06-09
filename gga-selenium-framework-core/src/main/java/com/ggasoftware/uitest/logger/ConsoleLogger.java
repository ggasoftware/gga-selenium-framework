package com.ggasoftware.uitest.logger;

import static com.ggasoftware.uitest.logger.LogLevels.*;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class ConsoleLogger implements ILogger {
    public void init(String message) { toLog(message, FATAL, "INIT"); }

    public void suit(String message) { toLog(message, FATAL, "SUIT"); }

    public void test(String message) { toLog(message, FATAL, "TEST"); }

    public void step(String message) {
        toLog(message, FATAL, "STEP");
    }

    public void fatal(String message) { toLog(message, FATAL); }

    public void error(String message) {
        toLog(message, ERROR);
    }

    public void warning(String message) {
        toLog(message, WARNING);
    }

    public void info(String message) {
        toLog(message, INFO);
    }

    public void debug(String message) {
        toLog(message, DEBUG);
    }

    public ConsoleLogger() { this.logLevel = INFO; }
    public ConsoleLogger(LogLevels logLevel) {
        this.logLevel = logLevel;
    }

    private LogLevels logLevel = INFO;
    public LogLevels getLogLevel() { return logLevel;  }

    private void toLog(String message, LogLevels msgLogLevel) {
        if (logLevel.lessThan(msgLogLevel))
            out.println(String.format("%s [%s]: %s ", currentTimeMillis(), msgLogLevel, message));
    }
    private void toLog(String message, LogLevels msgLogLevel, String prefix) {
        if (logLevel.lessThan(msgLogLevel))
            out.println(String.format("%s [%s]: %s ", currentTimeMillis(), msgLogLevel, message));
    }
}
