package com.ggasoftware.jdiuitests.core.utils.interfaces;

import com.ggasoftware.jdiuitests.core.logger.base.LogSettings;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.BaseElement;

/**
 * Created by 12345 on 20.11.2014.
 */
public interface IScenarioWithResult {
     <TResult> TResult invoke(
             BaseElement element, String actionName, JFuncT<TResult> viAction, JFuncTT<TResult, String> logResult, LogSettings logSettings) ;
}
