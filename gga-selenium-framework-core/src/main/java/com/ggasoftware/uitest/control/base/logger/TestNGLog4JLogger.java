package com.ggasoftware.uitest.control.base.logger;


import com.ggasoftware.uitest.control.base.logger.enums.BusinessInfoTypes;
import com.ggasoftware.uitest.control.base.logger.enums.LogInfoTypes;
import com.ggasoftware.uitest.control.base.logger.enums.LogLevels;

import static com.ggasoftware.uitest.control.base.logger.enums.LogLevels.INFO;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class TestNGLog4JLogger extends AbstractLogger {
    public static TestNGLog4JLogger logger = new TestNGLog4JLogger();
    private Log4JLogger log4J;
    private TestNGLogger testNGLogger;

    public TestNGLog4JLogger() {
        this(INFO);
    }
    public TestNGLog4JLogger(LogLevels logLevel) {
        super(logLevel);
        log4J = new Log4JLogger(logLevel);
        testNGLogger = new TestNGLogger(logLevel);
    }

    @Override
    public void inLog(String message, LogLevels logLevel, LogInfoTypes logInfoType) {
        testNGLogger.inLog(message, logLevel, logInfoType);
        log4J.inLog(message, logLevel, logInfoType);
    }

    @Override
    public void inLog(String message, BusinessInfoTypes infoType) {
        testNGLogger.inLog(message, infoType);
        log4J.inLog(message, infoType);
    }
}
