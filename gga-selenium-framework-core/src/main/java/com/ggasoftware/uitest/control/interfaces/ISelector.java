package com.ggasoftware.uitest.control.interfaces;

import com.ggasoftware.uitest.control.complex.table.ClickableText;

import java.util.List;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface ISelector {
                                // extends IList<TEnum, TElement>
    void select(String elementName);
    void select(Enum elementName);
    void select(int index);
    String isSelected();
    boolean isSelected(String elementName);
    boolean isSelected(Enum elementName);
    void selectNew();
    List<String> getAllLabels();
    String getAllLabelsAsText();
}
