package com.ggasoftware.jdiuitests.core.logger.base;

import com.ggasoftware.jdiuitests.core.logger.enums.LogInfoTypes;
import com.ggasoftware.jdiuitests.core.logger.enums.LogLevels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman_Iovlev on 7/13/2015.
 */
public class LogSettings {
    public LogLevels logLevels;
    private int logInfoTypes;
    public int getLogInfoTypes() { return logInfoTypes; }
    private LogInfoTypes logInfoType;
    public LogInfoTypes getLogInfoType() { return logInfoType; }

    public LogSettings() {
        this(LogLevels.INFO);
    }

    public LogSettings(LogLevels logLevel, LogInfoTypes... logInfoTypes) {
        this.logLevels = logLevel;
        switch (logInfoTypes.length) {
            case 0:
                this.logInfoTypes = LogInfoTypes.BUSINESS.type + LogInfoTypes.FRAMEWORK.type + LogInfoTypes.TECHNICAL.type;
                return;
            case 1:
                this.logInfoType = logInfoTypes[0];
                return;
            default:
                List<LogInfoTypes> usedTypes = new ArrayList<>();
                this.logInfoTypes = 0;
                for (LogInfoTypes type : logInfoTypes)
                    if (!usedTypes.contains(type)) {
                        usedTypes.add(type);
                        this.logInfoTypes += type.type;
                    }
        }
    }
}
