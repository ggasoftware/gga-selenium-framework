package com.ggasoftware.jdi_ui_tests.core.settings;

import com.ggasoftware.jdi_ui_tests.utils.common.Timer;

/**
 * Created by 12345 on 04.07.2015.
 */
public class TimeoutSettings {
    public int currentTimoutSec;

    public int waitElementSec = 20;
    public int waitPageLoadSec = 20;
    public int retryMSec = 100;

    public Timer timer() { return new Timer(currentTimoutSec * 1000); }

    public TimeoutSettings() { currentTimoutSec = waitPageLoadSec; }
    public void dropTimeouts() {
        currentTimoutSec = waitElementSec;
    }
}
