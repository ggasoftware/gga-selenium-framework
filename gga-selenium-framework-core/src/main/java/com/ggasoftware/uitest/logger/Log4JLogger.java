package com.ggasoftware.uitest.logger;

import com.ggasoftware.uitest.logger.enums.BusinessInfoTypes;
import com.ggasoftware.uitest.logger.enums.LogInfoTypes;
import com.ggasoftware.uitest.logger.enums.LogLevels;
import org.apache.log4j.Logger;

import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.frameworkName;
import static java.lang.String.format;
import static org.apache.log4j.LogManager.getLogger;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class Log4JLogger extends AbstractLogger {

    @Override
    public void inLog(String message, LogLevels logLevel, LogInfoTypes logInfoType) {
        switch (logLevel) {
            case FATAL: log4j.fatal(message); break;
            case ERROR: log4j.error(message); break;
            case WARNING: log4j.warn(message); break;
            case INFO: log4j.info(message); break;
            case DEBUG: log4j.debug(message); break;
        }
    }
    @Override
    public void inLog(String message, BusinessInfoTypes infoType) {
        log4j.info(format("[%s]: %s", infoType, message));
    }

    private Logger log4j = getLogger(frameworkName + " logger");

    public Log4JLogger() { super(); }
    public Log4JLogger(LogLevels logLevel) { super(logLevel); }

}
