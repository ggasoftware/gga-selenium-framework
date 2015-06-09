package com.ggasoftware.uitest.utils.interfaces;

import com.ggasoftware.uitest.control.Element;
import com.ggasoftware.uitest.utils.linqInterfaces.FuncT;

/**
 * Created by 12345 on 20.11.2014.
 */
public interface Scenario {
    <T> T invoke(Element viElement, String actionName, FuncT<T> viAction) throws Exception;
}
