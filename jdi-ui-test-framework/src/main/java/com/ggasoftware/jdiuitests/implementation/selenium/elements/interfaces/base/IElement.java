package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base;

import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncTT;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JDIAction;
import org.openqa.selenium.WebElement;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IElement extends IBaseElement, IVisible {
    /**
     * @returns Specified Selenium Element for this Element
     */
    @JDIAction
    WebElement getWebElement();

    /**
     * Get element attribute
     * @param name Specify name for attribute
     * @return Returns chosen attribute
     */
    @JDIAction
    String getAttribute(String name);

    /**
     * @param name Specify attribute name
     * @param value Specify attribute value
     * @return Waits while attribute gets expected value. Return false if this not happens
     */
    @JDIAction
    boolean waitAttribute(String name, String value);

    /**
     * @param attributeName Specify attribute name
     * @param value Specify attribute value
     * @return Sets attribute value for Element
     */
    @JDIAction
    void setAttribute(String attributeName, String value);

    /**
     * @param resultFunc Specify expected function result
     * @return Waits while condition with WebElement and returns wait result
     */
    @JDIAction
    Boolean wait(JFuncTT<WebElement, Boolean> resultFunc);

    /**
     * @param resultFunc Specify expected function result
     * @param condition Specify expected function condition
     * @return Waits while condition with WebElement happens and returns result using resultFunc
     */
    @JDIAction
    <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition);

    /**
     * @param resultFunc Specify expected function result
     * @param timeoutSec Specify timeout
     * @return Waits while condition with WebElement happens during specified timeout and returns wait result
     */
    @JDIAction
    Boolean wait(JFuncTT<WebElement, Boolean> resultFunc, int timeoutSec);

    /**
     * @param timeoutSec Specify timeout
     * @param condition Specify expected function condition
     * @param resultFunc Specify expected function result
     * @return Waits while condition with WebElement happens during specified timeout and returns result using resultFunc
     */
    @JDIAction
    <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition, int timeoutSec);
}
