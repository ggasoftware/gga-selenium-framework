package com.epam.ui_test_framework.utils.interfaces;

import com.epam.ui_test_framework.utils.linqInterfaces.JAction;
import com.epam.ui_test_framework.elements.BaseElement;

/**
 * Created by 12345 on 20.11.2014.
 */
public interface IScenario {
     void invoke(BaseElement element, String actionName, JAction viAction) ;
}
