package com.ggasoftware.jdiuitests.core.settings;

import static com.ggasoftware.jdiuitests.core.asserter.BaseChecker.setDefaulttimeout;

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
        setDefaulttimeout(timeoutSec * 1000);
    }

    public TimeoutSettings() { setCurrentTimeoutSec(20); }
    public void dropTimeouts() {
        setCurrentTimeoutSec(waitElementSec);
    }
}
