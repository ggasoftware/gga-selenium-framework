package com.ggasoftware.jdi_ui_tests.core.logger.base;

import com.ggasoftware.jdi_ui_tests.core.logger.enums.LogInfoTypes;
import com.ggasoftware.jdi_ui_tests.core.logger.enums.LogLevels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman_Iovlev on 7/13/2015.
 */
public class LogSettings {
    public LogLevels logLevel;
    public int logInfoTypes;
    public LogInfoTypes logInfoType;

    public LogSettings() {
        this(LogLevels.INFO);
    }
    public LogSettings(LogLevels logLevel, LogInfoTypes... logInfoTypes) {
        this.logLevel = logLevel;
        switch(logInfoTypes.length) {
            case 0:
                this.logInfoTypes = LogInfoTypes.BUSINESS.type + LogInfoTypes.FRAMEWORK.type + LogInfoTypes.TECHNICAL.type;
                return;
            case 1:
                this.logInfoType = logInfoTypes[0];
                return;
        }
        List<LogInfoTypes> usedTypes = new ArrayList<>();
        this.logInfoTypes = 0;
        for (LogInfoTypes logInfoType : logInfoTypes)
            if (!usedTypes.contains(logInfoType)) {
                usedTypes.add(logInfoType);
                this.logInfoTypes += logInfoType.type;
            }
    }
}
