package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations.JDIAction;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncTT;
import org.openqa.selenium.WebElement;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IElement extends IBaseElement, IVisible {
    /** Returns Selenium Element for this Element */
    @JDIAction
    WebElement getWebElement();
    /** Get element attribute */
    @JDIAction
    String getAttribute(String name);
    /** Waits while attribute gets expected value. Return false if this not happens */
    @JDIAction
    boolean waitAttribute(String name, String value);
    /** Sets attribute value for Element */
    @JDIAction
    void setAttribute(String attributeName, String value);
    /** Waits while condition with WebElement and returns wait result */
    @JDIAction
    Boolean wait(JFuncTT<WebElement, Boolean> resultFunc);
    /** Waits while condition with WebElement happens and returns result using resultFunc */
    @JDIAction
    <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition);
    /** Waits while condition with WebElement happens during specified timeout and returns wait result */
    @JDIAction
    Boolean wait(JFuncTT<WebElement, Boolean> resultFunc, int timeoutSec) ;
    /** Waits while condition with WebElement happens during specified timeout and returns result using resultFunc */
    @JDIAction
    <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition, int timeoutSec);
}
