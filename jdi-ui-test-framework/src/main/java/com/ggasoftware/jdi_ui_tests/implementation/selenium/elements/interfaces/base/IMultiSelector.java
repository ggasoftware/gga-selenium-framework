package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations.JDIAction;

import java.util.List;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IMultiSelector<TEnum extends Enum> extends IBaseElement, ISetValue {
    /** Select options with name (use text) from list (change their state selected/deselected) */
    @JDIAction
    void select(String... names);
    /** Select options with name (use enum) from list (change their state selected/deselected) */
    @JDIAction
    void select(TEnum... names);
    /** Select options with name (use index) from list (change their state selected/deselected) */
    @JDIAction
    void select(int... indexes);
    /** BaseChecker only specified options (use text) from list (all other options unchecked) */
    @JDIAction
    void check(String... names);
    /** BaseChecker only specified options (use enum) from list (all other options unchecked) */
    @JDIAction
    void check(TEnum... names);
    /** BaseChecker only specified options (use index) from list (all other options unchecked) */
    @JDIAction
    void check(int... indexes);
    /** Uncheck only specified options (use text) from list (all other options checked) */
    @JDIAction
    void uncheck(String... names);
    /** Uncheck only specified options (use enum) from list (all other options checked) */
    @JDIAction
    void uncheck(TEnum... names);
    /** Uncheck only specified options (use index) from list (all other options checked) */
    @JDIAction
    void uncheck(int... indexes);
    /** Get names of checked options */
    @JDIAction
    List<String> areSelected();
    /** Wait while all options with names (use text) selected. Return false if this not happens */
    @JDIAction
    boolean waitSelected(String... names);
    /** Wait while all options with names (use enum) selected. Return false if this not happens*/
    @JDIAction
    boolean waitSelected(TEnum... names);
    /** Get names of unchecked options */
    @JDIAction
    List<String> areDeselected();
    /** Wait while all options with names (use text) deselected. Return false if this not happens */
    @JDIAction
    boolean waitDeselected(String... names);
    /** Wait while all options with names (use enum) deselected. Return false if this not happens */
    @JDIAction
    boolean waitDeselected(TEnum... names);
    /** Get labels of all options */
    @JDIAction
    List<String> getOptions();
    /** Get all options labels in one string separated with “; ” */
    @JDIAction
    String getOptionsAsText();
    /** Set all options unchecked */
    @JDIAction
    void clear();
    /** Set all options unchecked */
    @JDIAction
    void uncheckAll();
    /** Set all options checked */
    @JDIAction
    void checkAll();
}
