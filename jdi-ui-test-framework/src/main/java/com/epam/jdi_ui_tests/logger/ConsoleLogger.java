package com.epam.jdi_ui_tests.logger;

import com.epam.jdi_ui_tests.logger.base.AbstractLogger;
import com.epam.jdi_ui_tests.logger.enums.BusinessInfoTypes;
import com.epam.jdi_ui_tests.logger.enums.LogInfoTypes;
import com.epam.jdi_ui_tests.logger.enums.LogLevels;

import static com.epam.jdi_ui_tests.utils.common.Timer.nowTime;
import static java.lang.String.format;
import static java.lang.System.out;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class ConsoleLogger extends AbstractLogger {
    @Override
    public void inLog(String message, LogLevels logLevel, LogInfoTypes logInfoType) {
        out.println(format("%s [%s-%s]: %s ", nowTime(), logInfoType, logLevel, message));
    }
    @Override
    public void inLog(String message, BusinessInfoTypes infoType) {
        out.println(format("%s [%s]: %s ", nowTime(), infoType, message));
    }

    public ConsoleLogger() { super(); }
    public ConsoleLogger(LogLevels logLevel) { super(logLevel); }
}
