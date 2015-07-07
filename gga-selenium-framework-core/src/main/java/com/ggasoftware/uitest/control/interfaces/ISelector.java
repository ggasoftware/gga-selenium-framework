package com.ggasoftware.uitest.control.interfaces;

import com.ggasoftware.uitest.utils.JDIAction;

import java.util.List;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface ISelector<TEnum extends Enum> extends IBaseElement, ISetValue {
    @JDIAction
    void select(String name);
    @JDIAction
    void select(TEnum name);
    @JDIAction
    void select(int index);
    @JDIAction
    String isSelected();
    @JDIAction
    boolean isSelected(String name);
    @JDIAction
    boolean isSelected(TEnum name);
    @JDIAction
    List<String> getOptions();
    @JDIAction
    String getOptionsAsText();
}
