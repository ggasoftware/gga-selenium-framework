package com.ggasoftware.uitest.control.base.logger.enums;

/**
 * Created by Roman_Iovlev on 6/30/2015.
 */
public enum LogInfoTypes {
    BUSINESS(2, 1),
    FRAMEWORK(1, 2),
    TECHNICAL(0, 4);

    public int num;
    public int type;

    LogInfoTypes(int num, int flag) {
        this.num = num;
        this.type = flag;
    }
}
