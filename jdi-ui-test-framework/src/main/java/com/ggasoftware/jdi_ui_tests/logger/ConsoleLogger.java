package com.ggasoftware.jdi_ui_tests.logger;

import com.ggasoftware.jdi_ui_tests.logger.base.AbstractLogger;
import com.ggasoftware.jdi_ui_tests.logger.enums.BusinessInfoTypes;
import com.ggasoftware.jdi_ui_tests.logger.enums.LogInfoTypes;
import com.ggasoftware.jdi_ui_tests.logger.enums.LogLevels;
import com.ggasoftware.jdi_ui_tests.utils.common.Timer;

import static com.ggasoftware.jdi_ui_tests.utils.common.Timer.nowTime;
import static java.lang.String.format;
import static java.lang.System.out;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class ConsoleLogger extends AbstractLogger {
    @Override
    public void inLog(String message, LogLevels logLevel, LogInfoTypes logInfoType) {
        out.println(format("%s [%s-%s]: %s ", Timer.nowTime(), logInfoType, logLevel, message));
    }
    @Override
    public void inLog(String message, BusinessInfoTypes infoType) {
        out.println(format("%s [%s]: %s ", Timer.nowTime(), infoType, message));
    }

    public ConsoleLogger() { super(); }
    public ConsoleLogger(LogLevels logLevel) { super(logLevel); }
}
