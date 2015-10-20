package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex;

import com.ggasoftware.jdiuitests.core.utils.map.MapArray;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IHasValue;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IVisible;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JDIAction;
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

    /**
     * @param name Specify string by String mechanic
     * @return Get textList’s text by specified param
     */
    @JDIAction
    String getText(String name);

    /**
     * @param index Specify string by Integer mechanic
     * @return Get textList’s text by specified param
     */
    @JDIAction
    String getText(int index);

    /**
     * @param enumName Specify string by Enum mechanic
     * @return Get textList’s text by specified param
     */
    @JDIAction
    String getText(TEnum enumName);

    /**
    * @return Returns strings count
    */
    @JDIAction
    int count();

    /**
     * @return Wait while TextList’s text contains expected text. Returns Element’s text
     */
    @JDIAction
    List<String> waitText(String str);


    @JDIAction
    List<String> getTextList();

    @JDIAction
    String getFirstText();

    @JDIAction
    String getLastText();
}
