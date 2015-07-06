package com.ggasoftware.uitest.utils.interfaces;

import com.ggasoftware.uitest.control.BaseElement;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncT;
import com.ggasoftware.uitest.utils.linqInterfaces.JFuncTT;

/**
 * Created by 12345 on 20.11.2014.
 */
public interface IScenarioWithResult {
     <TResult> TResult invoke(
             BaseElement element, String actionName, JFuncT<TResult> viAction, JFuncTT<TResult, String> logResult) ;
}
