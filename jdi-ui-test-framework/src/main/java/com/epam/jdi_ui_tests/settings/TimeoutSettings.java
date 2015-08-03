package com.epam.jdi_ui_tests.settings;

/**
 * Created by 12345 on 04.07.2015.
 */
public class TimeoutSettings {
    public int currentTimoutSec;

    public int waitElementSec = 3;
    public int waitPageLoadSec = 20;
    public int retryMSec = 100;

    public TimeoutSettings() { currentTimoutSec = waitPageLoadSec; }
    public void dropTimeouts() {
        currentTimoutSec = waitElementSec;
    }
}
