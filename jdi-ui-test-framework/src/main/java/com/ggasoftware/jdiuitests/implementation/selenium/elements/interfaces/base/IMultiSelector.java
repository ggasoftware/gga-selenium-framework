package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JDIAction;

import java.util.List;

import static com.ggasoftware.jdiuitests.core.utils.common.PrintUtils.print;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IMultiSelector<TEnum extends Enum> extends IBaseElement, ISetValue {
    /**
     * @param names Specify names
     * @return Select options with name (use text) from list (change their state selected/deselected)
     */
    @JDIAction
    void select(String... names);

    /**
     * @param names Specify names
     * @return Select options with name (use enum) from list (change their state selected/deselected)
     */
    @JDIAction
    void select(TEnum... names);

    /**
     * @param indexes Specify indexes
     * @return Select options with name (use index) from list (change their state selected/deselected)
     */
    @JDIAction
    void select(int... indexes);

    /**
     * @param names Specify names
     * @return BaseChecker only specified options (use text) from list (all other options unchecked)
     */
    @JDIAction
    void check(String... names);

    /**
     * @param names Specify names
     * @return BaseChecker only specified options (use enum) from list (all other options unchecked)
     */
    @JDIAction
    void check(TEnum... names);

    /**
     * @param indexes Specify indexes
     * @return BaseChecker only specified options (use index) from list (all other options unchecked)
     */
    @JDIAction
    void check(int... indexes);

    /**
     * @param names Specify names
     * @return Uncheck only specified options (use text) from list (all other options checked)
     */
    @JDIAction
    void uncheck(String... names);

    /**
     * @param names Specify names
     * @return Uncheck only specified options (use enum) from list (all other options checked)
     */
    @JDIAction
    void uncheck(TEnum... names);

    /**
     * @param indexes Specify indexes
     * @return Uncheck only specified options (use index) from list (all other options checked)
     */
    @JDIAction
    void uncheck(int... indexes);

    /**
     * @return Get names of checked options
     */
    @JDIAction
    List<String> areSelected();

    /**
     * @param names Specify names
     * @return Wait while all options with names (use text) selected. Return false if this not happens
     */
    @JDIAction
    boolean waitSelected(String... names);

    /**
     * @param names Specify names
     * @return Wait while all options with names (use enum) selected. Return false if this not happens
     */
    @JDIAction
    boolean waitSelected(TEnum... names);

    /**
     * @return Get names of unchecked options
     */
    @JDIAction
    List<String> areDeselected();

    /**
     * @param names Specify names
     * @return Wait while all options with names (use text) deselected. Return false if this not happens
     */
    @JDIAction
    boolean waitDeselected(String... names);

    /**
     * @param names Specify names
     * @return Wait while all options with names (use enum) deselected. Return false if this not happens
     */
    @JDIAction
    boolean waitDeselected(TEnum... names);

    /**
     * @return Get labels of all options
     */
    @JDIAction
    List<String> getOptions();

    /**
     * @return Get labels of all options
     */
    @JDIAction
    default List<String> getNames() {
        return getOptions();
    }

    /**
     * @return Get labels of all options
     */
    @JDIAction
    default List<String> getValues() {
        return getOptions();
    }

    /**
     * @return Get all options labels in one string separated with “; ”
     */
    @JDIAction
    default String getOptionsAsText() {
        return print(getOptions());
    }

    /**
     * @return Set all options checked
     */
    @JDIAction
    void checkAll();

    /**
     * @return Set all options checked
     */
    @JDIAction
    default void selectAll() {
        checkAll();
    }

    /**
     * @return Set all options unchecked
     */
    @JDIAction
    void clear();

    /**
     * @return Set all options unchecked
     */
    @JDIAction
    default void uncheckAll() {
        clear();
    }
}
