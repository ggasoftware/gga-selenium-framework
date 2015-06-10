package com.ggasoftware.uitest.control.interfaces;

import java.util.List;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface ISelector<TEnum extends Enum, TElement extends IClickable & IText> extends IList<TEnum, TElement> {
    void select(String elementName);
    void select(TEnum elementName);
    void select(int index);
    String isSelected();
    boolean isSelected(String elementName);
    boolean isSelected(TEnum elementName);
    boolean isSelected(int index);
    void selectNew();
    List<String> getAllValues();
    String getAllValuesAsText();
}
