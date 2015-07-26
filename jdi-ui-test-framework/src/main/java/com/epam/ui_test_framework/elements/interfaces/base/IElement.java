package com.epam.ui_test_framework.elements.interfaces.base;

import com.epam.ui_test_framework.elements.page_objects.annotations.JDIAction;
import com.epam.ui_test_framework.utils.common.Timer;
import com.epam.ui_test_framework.utils.linqInterfaces.JFuncTT;
import org.openqa.selenium.WebElement;

import static com.epam.ui_test_framework.settings.FrameworkSettings.timeouts;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IElement extends IBaseElement, IVisible {
    @JDIAction
    WebElement getWebElement();
    @JDIAction
    void setAttribute(String attributeName, String value);
    @JDIAction
    Boolean wait(JFuncTT<WebElement, Boolean> resultFunc);
    @JDIAction
    <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition);
    @JDIAction
    Boolean wait(JFuncTT<WebElement, Boolean> resultFunc, int timeoutSec) ;
    @JDIAction
    <T> T wait(JFuncTT<WebElement, T> resultFunc, JFuncTT<T, Boolean> condition, int timeoutSec);
}
