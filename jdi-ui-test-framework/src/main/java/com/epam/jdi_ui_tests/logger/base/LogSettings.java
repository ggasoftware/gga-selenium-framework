package com.epam.jdi_ui_tests.logger.base;

import com.epam.jdi_ui_tests.logger.enums.LogInfoTypes;
import com.epam.jdi_ui_tests.logger.enums.LogLevels;

import java.util.ArrayList;
import java.util.List;

import static com.epam.jdi_ui_tests.logger.enums.LogInfoTypes.*;
import static com.epam.jdi_ui_tests.logger.enums.LogLevels.*;

/**
 * Created by Roman_Iovlev on 7/13/2015.
 */
public class LogSettings {
    public LogLevels logLevel;
    public int logInfoTypes;
    public LogInfoTypes logInfoType;

    public LogSettings() {
        this(INFO);
    }
    public LogSettings(LogLevels logLevel, LogInfoTypes... logInfoTypes) {
        this.logLevel = logLevel;
        switch(logInfoTypes.length) {
            case 0:
                this.logInfoTypes = BUSINESS.type + FRAMEWORK.type + TECHNICAL.type;
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
