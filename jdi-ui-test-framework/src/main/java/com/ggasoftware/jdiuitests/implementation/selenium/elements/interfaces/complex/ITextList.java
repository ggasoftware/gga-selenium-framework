package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IHasValue;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IVisible;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations.JDIAction;
import com.ggasoftware.jdiuitests.core.utils.map.MapArray;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface ITextList<TEnum extends Enum> extends IBaseElement, IHasValue, IVisible {
    List<WebElement> getWebElements();
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
    List<String> getTextList();
    @JDIAction
    String getFirstText();
    @JDIAction
    String getLastText();
}
