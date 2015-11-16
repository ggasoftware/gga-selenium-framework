package com.ggasoftware.jdiuitest.core.utils.interfaces;

import com.ggasoftware.jdiuitest.core.interfaces.base.IBaseElement;
import com.ggasoftware.jdiuitest.core.logger.base.LogSettings;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JAction;

/**
 * Created by 12345 on 20.11.2014.
 */
public interface IScenario {
    void invoke(IBaseElement element, String actionName, JAction viAction, LogSettings logSettings);
}
