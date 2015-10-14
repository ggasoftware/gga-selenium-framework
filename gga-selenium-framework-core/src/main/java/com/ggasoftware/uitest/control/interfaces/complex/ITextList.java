package com.ggasoftware.uitest.control.interfaces.complex;

import com.ggasoftware.uitest.control.base.annotations.JDIAction;
import com.ggasoftware.uitest.control.base.map.MapArray;
import com.ggasoftware.uitest.control.interfaces.base.IBaseElement;
import com.ggasoftware.uitest.control.interfaces.base.IHaveValue;
import com.ggasoftware.uitest.control.interfaces.base.IVisible;
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
    List<String> getTextList();

    @JDIAction
    String getFirstText();

    @JDIAction
    String getLastText();
}
