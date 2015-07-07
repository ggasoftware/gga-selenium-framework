package com.ggasoftware.uitest.control.interfaces;

import com.ggasoftware.uitest.utils.JDIAction;
import com.ggasoftware.uitest.utils.map.MapArray;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IList<TEnum extends Enum> extends IBaseElement, IHaveValue {
    List<WebElement> getWebElements();
    List<WebElement> getWebElements(int timeouInSec);
    WebElement getElement(String name);
    WebElement getElement(int index);
    WebElement getElement(TEnum enumName);
    MapArray<String, WebElement> getElements();
    @JDIAction
    String getText(String name);
    @JDIAction
    String getText(int name);
    @JDIAction
    String getText(TEnum enumName);
    @JDIAction
    int count();
}
