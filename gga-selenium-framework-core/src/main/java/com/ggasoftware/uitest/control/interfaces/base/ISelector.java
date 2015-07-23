package com.ggasoftware.uitest.control.interfaces.base;

import com.ggasoftware.uitest.control.base.annotations.JDIAction;

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
    boolean waitSelected(String name);
    @JDIAction
    boolean waitSelected(TEnum name);
    @JDIAction
    List<String> getOptions();
    @JDIAction
    String getOptionsAsText();
}
