package com.ggasoftware.jdi_ui_tests.logger.enums;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public enum LogLevels {
    OFF(-1),        // No logging
    FATAL(0),       // Unexpected errors
    ERROR(3),       // Critical errors
    WARNING(4),     // Erorrs due to wrong params
    INFO(6),        // Actions Info
    DEBUG(7),       // Debug info (not for prod)
    ALL(100);       // All log messages

    private int priority;
    public int getPriority() { return priority; }
    public boolean equalOrLessThan(LogLevels level) {
        return getPriority() >= level.getPriority();
    }
    LogLevels(int priority) {
        this.priority = priority;
    }
}
