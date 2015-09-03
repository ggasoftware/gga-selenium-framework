package com.ggasoftware.jdi_ui_tests.core.utils.interfaces;

import com.ggasoftware.jdi_ui_tests.core.logger.base.LogSettings;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JAction;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.BaseElement;

/**
 * Created by 12345 on 20.11.2014.
 */
public interface IScenario {
     void invoke(BaseElement element, String actionName, JAction viAction, LogSettings logSettings) ;
}
