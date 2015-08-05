package com.ggasoftware.jdi_ui_tests.utils.interfaces;

import com.ggasoftware.jdi_ui_tests.logger.base.LogSettings;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdi_ui_tests.elements.BaseElement;

/**
 * Created by 12345 on 20.11.2014.
 */
public interface IScenarioWithResult {
     <TResult> TResult invoke(
             BaseElement element, String actionName, JFuncT<TResult> viAction, JFuncTT<TResult, String> logResult, LogSettings logSettings) ;
}
