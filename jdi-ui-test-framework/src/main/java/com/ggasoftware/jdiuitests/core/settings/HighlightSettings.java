package com.ggasoftware.jdiuitests.core.settings;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class HighlightSettings {
    private String bgColor = "yellow";
    private String frameColor = "red";
    private int timeoutInSec = 2;

    public HighlightSettings() {
    }

    public HighlightSettings(String bgColor, String frameColor, int timeoutInSec) {
        this.bgColor = bgColor;
        this.frameColor = frameColor;
        this.timeoutInSec = timeoutInSec;
    }

    public HighlightSettings setBgColor(String bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public HighlightSettings setFrameColor(String frameColor) {
        this.frameColor = frameColor;
        return this;
    }

    public HighlightSettings setTimeoutInSec(int timeoutInSec) {
        this.timeoutInSec = timeoutInSec;
        return this;
    }
    public String getBgColor() {
        return bgColor;
    }

    public String getFrameColor() {
        return frameColor;
    }

    public int getTimeoutInSec() {
        return timeoutInSec;
    }
}

