package com.ggasoftware.uitest.utils.interfaces;

import com.ggasoftware.uitest.control.BaseElement;
import com.ggasoftware.uitest.control.Element;
import com.ggasoftware.uitest.utils.linqInterfaces.*;
import org.openqa.selenium.WebDriver;

/**
 * Created by 12345 on 20.11.2014.
 */
public interface Scenario {
    // TODO change to TElement extends Element while Element lose Parent
     <TResult> TResult invoke(
             BaseElement element, String actionName, JFuncT<TResult> viAction) ;
}
