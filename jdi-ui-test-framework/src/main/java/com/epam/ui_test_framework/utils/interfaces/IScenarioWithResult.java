package com.epam.ui_test_framework.utils.interfaces;

import com.epam.ui_test_framework.logger.base.LogSettings;
import com.epam.ui_test_framework.utils.linqInterfaces.JFuncT;
import com.epam.ui_test_framework.utils.linqInterfaces.JFuncTT;
import com.epam.ui_test_framework.elements.BaseElement;

/**
 * Created by 12345 on 20.11.2014.
 */
public interface IScenarioWithResult {
     <TResult> TResult invoke(
             BaseElement element, String actionName, JFuncT<TResult> viAction, JFuncTT<TResult, String> logResult, LogSettings logSettings) ;
}
