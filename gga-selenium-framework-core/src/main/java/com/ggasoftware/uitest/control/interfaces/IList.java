package com.ggasoftware.uitest.control.interfaces;

import com.ggasoftware.uitest.utils.JDIAction;
import com.ggasoftware.uitest.utils.map.MapArray;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IList<TEnum extends Enum> extends IBaseElement, IHaveValue {
    List<WebElement> getWebElements() throws Exception;
    List<WebElement> getWebElements(int timeouInSec) throws Exception;
    WebElement getElement(String name) throws Exception;
    WebElement getElement(int index) throws Exception;
    WebElement getElement(TEnum enumName) throws Exception;
    MapArray<String, WebElement> getElements() throws Exception;
    @JDIAction
    String getText(String name) throws Exception;
    @JDIAction
    String getText(int name) throws Exception;
    @JDIAction
    String getText(TEnum enumName) throws Exception;
    @JDIAction
    int count() throws Exception;
}
