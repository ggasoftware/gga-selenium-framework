package com.ggasoftware.jdi_ui_tests.logger.enums;

/**
 * Created by Roman_Iovlev on 6/30/2015.
 */
public enum LogInfoTypes {
    BUSINESS(1),
    FRAMEWORK(2),
    TECHNICAL(4);

    public int type;
    LogInfoTypes(int flag) {
        this.type = flag;
    }
}
