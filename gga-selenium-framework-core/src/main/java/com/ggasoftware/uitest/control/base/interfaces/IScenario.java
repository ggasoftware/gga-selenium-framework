package com.ggasoftware.uitest.control.base.interfaces;


import com.ggasoftware.uitest.control.new_controls.base.BaseElement;
import com.ggasoftware.uitest.utils.linqInterfaces.JAction;

/**
 * Created by 12345 on 20.11.2014.
 */
public interface IScenario {
    void invoke(BaseElement element, String actionName, JAction viAction);
}
