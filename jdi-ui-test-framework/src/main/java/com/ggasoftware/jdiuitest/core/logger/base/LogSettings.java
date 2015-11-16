package com.ggasoftware.jdiuitest.core.logger.base;

import com.ggasoftware.jdiuitest.core.logger.enums.LogInfoTypes;
import com.ggasoftware.jdiuitest.core.logger.enums.LogLevels;

import java.util.ArrayList;
import java.util.List;

import static com.ggasoftware.jdiuitest.core.logger.enums.LogInfoTypes.*;

/**
 * Created by Roman_Iovlev on 7/13/2015.
 */
public class LogSettings {
    public LogLevels logLevels;
    private int logInfoTypes;
    public int getLogInfoTypes() {
        return logInfoTypes;
    }
    private LogInfoTypes logInfoType;
    public LogInfoTypes getLogInfoType() {
        return logInfoType;
    }

    public LogSettings() {
        this(LogLevels.INFO);
    }

    public LogSettings(LogLevels logLevel, LogInfoTypes... logInfoTypes) {
        this.logLevels = logLevel;
        switch (logInfoTypes.length) {
            case 0:
                this.logInfoTypes = BUSINESS.type + FRAMEWORK.type + TECHNICAL.type;
                return;
            case 1:
                this.logInfoType = logInfoTypes[0];
                return;
            default:
                setLogSettings(logInfoTypes);
        }
    }

    private void setLogSettings(LogInfoTypes[] logInfoTypes) {
        List<LogInfoTypes> usedTypes = new ArrayList<>();
        this.logInfoTypes = 0;
        for (LogInfoTypes type : logInfoTypes)
            if (!usedTypes.contains(type)) {
                usedTypes.add(type);
                this.logInfoTypes += type.type;
            }
    }
}
