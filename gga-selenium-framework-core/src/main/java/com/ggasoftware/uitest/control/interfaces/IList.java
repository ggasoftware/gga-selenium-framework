package com.ggasoftware.uitest.control.interfaces;

import com.ggasoftware.uitest.utils.map.MapArray;
import org.openqa.selenium.By;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IList<TEnum extends Enum, TElement extends IText> extends IHaveValue {
    TElement getElement(String name);
    TElement getElement(int name);
    TElement getElement(TEnum enumName);
    MapArray<String, TElement> getElements(String name);
    String getText(String name);
    String getText(int name);
    String getText(TEnum enumName);
    int count();
}
