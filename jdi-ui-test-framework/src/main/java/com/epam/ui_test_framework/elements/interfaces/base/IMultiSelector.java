package com.epam.ui_test_framework.elements.interfaces.base;

import com.epam.ui_test_framework.utils.JDIAction;

import java.util.List;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IMultiSelector<TEnum extends Enum> extends IBaseElement, ISetValue {
    @JDIAction
    void select(String... names);
    @JDIAction
    void select(TEnum... names);
    @JDIAction
    void select(int... indexes);
    @JDIAction
    void selectOnly(String... names);
    @JDIAction
    void selectOnly(TEnum... names);
    @JDIAction
    void selectOnly(int... indexes);
    @JDIAction
    void deselectOnly(String... names);
    @JDIAction
    void deselectOnly(TEnum... names);
    @JDIAction
    void deselectOnly(int... indexes);
    @JDIAction
    List<String> areSelected();
    boolean areSelected(String... names);
    @JDIAction
    boolean areSelected(TEnum... names);
    @JDIAction
    List<String> areDeselected();
    @JDIAction
    boolean areDeselected(String... names);
    @JDIAction
    boolean areDeselected(TEnum... names);
    @JDIAction
    String getOptionsAsText();
    @JDIAction
    void clear();
    @JDIAction
    void deselectAll();
    @JDIAction
    void selectAll();
}
