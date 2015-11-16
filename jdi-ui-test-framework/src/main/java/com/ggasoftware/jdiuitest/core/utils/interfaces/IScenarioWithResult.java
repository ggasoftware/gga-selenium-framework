package com.ggasoftware.jdiuitest.core.utils.interfaces;

import com.ggasoftware.jdiuitest.core.interfaces.base.IBaseElement;
import com.ggasoftware.jdiuitest.core.logger.base.LogSettings;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncT;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncTT;

/**
 * Created by 12345 on 20.11.2014.
 */
public interface IScenarioWithResult {
    <TResult> TResult invoke(
            IBaseElement element, String actionName, JFuncT<TResult> viAction, JFuncTT<TResult, String> logResult, LogSettings logSettings);
}
