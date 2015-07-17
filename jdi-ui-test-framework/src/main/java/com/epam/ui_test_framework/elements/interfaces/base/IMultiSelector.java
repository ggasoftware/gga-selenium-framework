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
    void check(String... names);
    @JDIAction
    void check(TEnum... names);
    @JDIAction
    void check(int... indexes);
    @JDIAction
    void uncheck(String... names);
    @JDIAction
    void uncheck(TEnum... names);
    @JDIAction
    void uncheck(int... indexes);
    @JDIAction
    List<String> areSelected();
    boolean waitSelected(String... names);
    @JDIAction
    boolean waitSelected(TEnum... names);
    @JDIAction
    List<String> areDeselected();
    @JDIAction
    boolean waitDeselected(String... names);
    @JDIAction
    boolean waitDeselected(TEnum... names);
    @JDIAction
    List<String> getOptions();
    @JDIAction
    String getOptionsAsText();
    @JDIAction
    void clear();
    @JDIAction
    void uncheckAll();
    @JDIAction
    void checkAll();
}
