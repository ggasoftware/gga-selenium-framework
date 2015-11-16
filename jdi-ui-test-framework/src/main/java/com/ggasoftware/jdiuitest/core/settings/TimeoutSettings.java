package com.ggasoftware.jdiuitest.core.settings;

import static com.ggasoftware.jdiuitest.core.asserter.BaseChecker.setDefaultTimeout;

/**
 * Created by 12345 on 04.07.2015.
 */
public class TimeoutSettings {
    public int currentTimeoutSec;

    public int waitElementSec = 20;
    public int waitPageLoadSec = 20;
    public int retryMSec = 100;

    public TimeoutSettings() {
        setCurrentTimeoutSec(20);
    }

    public void setCurrentTimeoutSec(int timeoutSec) {
        currentTimeoutSec = timeoutSec;
        setDefaultTimeout(timeoutSec * 1000L);
    }

    public void dropTimeouts() {
        setCurrentTimeoutSec(waitElementSec);
    }
}
