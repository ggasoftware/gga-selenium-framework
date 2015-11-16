package com.ggasoftware.jdiuitest.core.interfaces.settings;

import com.ggasoftware.jdiuitest.core.interfaces.base.IElement;
import com.ggasoftware.jdiuitest.core.settings.HighlightSettings;

/**
 * Created by Roman_Iovlev on 9/3/2015.
 */
public interface IDriver {
    void registerDriver(String driverName);
    void setRunType(String runType);
    <T> T getDriver();
    boolean hasDrivers();
    String currentDriverName();
    <T> T getDriver(String name);
    void highlight(IElement element);
    void highlight(IElement element, HighlightSettings highlightSettings);
    String getDriverPath();
    void setDriverPath(String driverPath);
}
