package com.ggasoftware.jdi_ui_tests.settings;

import com.ggasoftware.jdi_ui_tests.asserter.BaseChecker;

/**
 * Created by 12345 on 04.07.2015.
 */
public class TimeoutSettings {
    public int currentTimeoutSec;

    public int waitElementSec = 20;
    public int waitPageLoadSec = 20;
    public int retryMSec = 100;

    public void setCurrentTimeoutSec(int timeoutSec) {
        currentTimeoutSec = timeoutSec;
        BaseChecker.defaultWaitTimeout = timeoutSec;
    }

    public TimeoutSettings() { setCurrentTimeoutSec(waitPageLoadSec); }
    public void dropTimeouts() {
        setCurrentTimeoutSec(waitElementSec);
    }
}
