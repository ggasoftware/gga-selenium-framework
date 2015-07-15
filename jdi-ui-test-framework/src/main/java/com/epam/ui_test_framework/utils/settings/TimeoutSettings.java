package com.epam.ui_test_framework.utils.settings;

/**
 * Created by 12345 on 04.07.2015.
 */
public class TimeoutSettings {
    public int currentTimoutSec;

    // TODO need to create page loads await mechanism
    // public int waitElementSec = 3;
    public int waitElementSec = 15;
    public int waitPageLoadSec = 20;
    public int retryMSec = 100;

    public TimeoutSettings() {
        currentTimoutSec = waitPageLoadSec;
    }
    public void dropTimeouts() {
        currentTimoutSec = waitElementSec;
    }
}
