package com.epam.jdi_ui_tests.utils.interfaces;

import com.epam.jdi_ui_tests.logger.base.LogSettings;
import com.epam.jdi_ui_tests.utils.linqInterfaces.JAction;
import com.epam.jdi_ui_tests.elements.BaseElement;

/**
 * Created by 12345 on 20.11.2014.
 */
public interface IScenario {
     void invoke(BaseElement element, String actionName, JAction viAction, LogSettings logSettings) ;
}
