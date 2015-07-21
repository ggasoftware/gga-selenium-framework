package com.epam.ui_test_framework.elements.interfaces.complex;

import com.epam.ui_test_framework.elements.interfaces.base.IBaseElement;
import com.epam.ui_test_framework.elements.interfaces.base.IHaveValue;
import com.epam.ui_test_framework.elements.interfaces.base.IVisible;
import com.epam.ui_test_framework.utils.JDIAction;
import com.epam.ui_test_framework.utils.map.MapArray;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface ITextList<TEnum extends Enum> extends IBaseElement, IHaveValue, IVisible {
    List<WebElement> getWebElements();
    List<WebElement> getWebElements(int timeouInSec);
    WebElement getElement(String name);
    WebElement getElement(int index);
    WebElement getElement(TEnum enumName);
    MapArray<String, WebElement> getElements();
    @JDIAction
    String getText(String name);
    @JDIAction
    String getText(int index);
    @JDIAction
    String getText(TEnum enumName);
    @JDIAction
    int count();
    @JDIAction
    List<String> waitText(String str);
    @JDIAction
    String getLastText();
}
