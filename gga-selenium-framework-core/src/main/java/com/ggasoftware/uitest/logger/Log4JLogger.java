package com.ggasoftware.uitest.logger;

import com.ggasoftware.uitest.utils.TestBaseWebDriver;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import static com.ggasoftware.uitest.logger.LogLevels.*;
import static com.ggasoftware.uitest.utils.TestBaseWebDriver.frameworkName;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class Log4JLogger implements ILogger {
    public void init(String message) {
        consoleLogger.init(message);
        if (logLevel.lessThan(FATAL)) {
            log4j.info("[INIT]: " + message);
        }
    }

    public void suit(String message) {
        consoleLogger.suit(message);
        if (logLevel.lessThan(FATAL)) {
            log4j.info("[SUIT]: " + message);
        }
    }

    public void test(String message) {
        consoleLogger.test(message);
        if (logLevel.lessThan(FATAL)) {
            log4j.info("[TEST]: " + message);
        }
    }

    public void step(String message) {
        consoleLogger.step(message);
        if (logLevel.lessThan(FATAL)) {
            log4j.info("[STEP]: " + message);
        }
    }

    public void fatal(String message) {
        consoleLogger.fatal(message);
        if (logLevel.lessThan(FATAL)) {
            log4j.fatal(message);
        }
    }

    public void error(String message) {
        consoleLogger.error(message);
        if (logLevel.lessThan(ERROR))
            log4j.error(message);
    }

    public void warning(String message) {
        consoleLogger.warning(message);
        if (logLevel.lessThan(WARNING))
            log4j.warn(message);
    }

    public void info(String message) {
        consoleLogger.info(message);
        if (logLevel.lessThan(INFO))
            log4j.info(message);
    }

    public void debug(String message) {
        consoleLogger.debug(message);
        if (logLevel.lessThan(DEBUG))
            log4j.debug(message);
    }

    private Logger log4j = LogManager.getLogger(frameworkName + " logger");
    private ConsoleLogger consoleLogger;

    public Log4JLogger() {
        this.consoleLogger = new ConsoleLogger(INFO);
    }
    public Log4JLogger(LogLevels logLevel) {
        this.logLevel = logLevel;
        this.consoleLogger = new ConsoleLogger(logLevel);
    }

    private LogLevels logLevel = INFO;
    public LogLevels getLogLevel() {
        return logLevel;
    }
}
