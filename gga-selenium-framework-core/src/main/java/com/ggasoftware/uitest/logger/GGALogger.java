package com.ggasoftware.uitest.logger;

import com.ggasoftware.uitest.logger.enums.BusinessInfoTypes;
import com.ggasoftware.uitest.logger.enums.LogInfoTypes;
import com.ggasoftware.uitest.logger.enums.LogLevels;

import static com.ggasoftware.uitest.logger.enums.LogInfoTypes.BUSINESS;
import static com.ggasoftware.uitest.logger.enums.LogLevels.INFO;
import static com.ggasoftware.uitest.utils.Timer.nowTime;
import static java.lang.String.format;
import static org.testng.Reporter.log;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class GGALogger extends AbstractLogger {
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

    public GGALogger() { this(INFO); }
    public GGALogger(LogLevels logLevel) {
        super(logLevel);
        log4J = new Log4JLogger(logLevel);
        testNGLogger = new TestNGLogger(logLevel);
    }
}
