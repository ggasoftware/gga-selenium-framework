package com.ggasoftware.uitest.control.base.interfaces;


import com.ggasoftware.uitest.control.base.logger.LogSettings;
import com.ggasoftware.uitest.control.new_controls.base.BaseElement;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncT;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncTT;

/**
 * Created by 12345 on 20.11.2014.
 */
public interface IScenarioWithResult {
    <TResult> TResult invoke(
            BaseElement element, String actionName, JFuncT<TResult> viAction, JFuncTT<TResult, String> logResult, LogSettings logSettings);
}
