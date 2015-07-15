package com.epam.ui_test_framework.utils.settings;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class HighlightSettings {    public String BgColor = "yellow";
    public String FrameColor = "red";
    public int TimeoutInSec = 2;

    public HighlightSettings() {}
    public HighlightSettings(String bgColor, String frameColor, int timeoutInSec) {
        BgColor = bgColor;
        FrameColor = frameColor;
        TimeoutInSec = timeoutInSec;
    }
    public HighlightSettings setBgColor(String bgColor) { BgColor = bgColor; return this; }
    public HighlightSettings setFrameColor(String frameColor) { FrameColor = frameColor; return this; }
    public HighlightSettings setTimeoutInSec(int timeoutInSec) { TimeoutInSec = timeoutInSec; return this; }
}

