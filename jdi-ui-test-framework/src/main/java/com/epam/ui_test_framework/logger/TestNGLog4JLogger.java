package com.epam.ui_test_framework.logger;

import com.epam.ui_test_framework.logger.enums.BusinessInfoTypes;
import com.epam.ui_test_framework.logger.enums.LogInfoTypes;
import com.epam.ui_test_framework.logger.enums.LogLevels;

import static com.epam.ui_test_framework.logger.enums.LogLevels.INFO;
import static com.epam.ui_test_framework.utils.common.Timer.nowTime;
import static java.lang.String.format;
import static org.testng.Reporter.log;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class TestNGLog4JLogger extends AbstractLogger {
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

    private Log4JLogger log4J;
    private TestNGLogger testNGLogger;

    public TestNGLog4JLogger() { this(INFO); }
    public TestNGLog4JLogger(LogLevels logLevel) {
        super(logLevel);
        log4J = new Log4JLogger(logLevel);
        testNGLogger = new TestNGLogger(logLevel);
    }
}
