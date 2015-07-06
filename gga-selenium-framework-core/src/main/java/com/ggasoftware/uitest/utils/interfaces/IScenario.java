package com.ggasoftware.uitest.utils.interfaces;

import com.ggasoftware.uitest.control.BaseElement;
import com.ggasoftware.uitest.utils.linqInterfaces.*;

/**
 * Created by 12345 on 20.11.2014.
 */
public interface IScenario {
     void invoke(BaseElement element, String actionName, JAction viAction) ;
}
