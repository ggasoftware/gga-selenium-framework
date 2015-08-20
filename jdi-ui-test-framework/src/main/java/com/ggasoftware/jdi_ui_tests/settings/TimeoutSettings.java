package com.ggasoftware.jdi_ui_tests.settings;

/**
 * Created by 12345 on 04.07.2015.
 */
public class TimeoutSettings {
    public int currentTimeoutSec;

    public int waitElementSec = 20;
    public int waitPageLoadSec = 20;
    public int retryMSec = 100;

    public TimeoutSettings() { currentTimeoutSec = waitPageLoadSec; }
    public void dropTimeouts() {
        currentTimeoutSec = waitElementSec;
    }
}
