package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex;

import com.ggasoftware.jdiuitests.core.utils.common.PrintUtils;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.ISetValue;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JDIAction;

import java.util.List;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface ISelector<TEnum extends Enum> extends IBaseElement, ISetValue {
    /**
     * @param name Specify name using string
     * @return elect Element with name (use text) from list
     */
    @JDIAction
    void select(String name);

    /**
     * @param name Specify name using enum
     * @return Select Element with name (use enum) from list
     */
    @JDIAction
    void select(TEnum name);

    /**
     * @param index Specify digit to select
     * @return Select Element with name (use index) from list
     */
    @JDIAction
    void select(int index);

    /**
     * @return Get name of the selected Element
     */
    @JDIAction
    String getSelected();

    /**
     * @return Get index of the selected Element
     */
    @JDIAction
    int getSelectedIndex();

    /**
     * @param name Specify name using string
     * @return Is option selected?
     */
    @JDIAction
    boolean isSelected(String name);

    /**
     * @param name Specify name using enum
     * @return Is option selected?
     */
    @JDIAction
    boolean isSelected(TEnum name);

    /**
     * @param name Specify name using string
     * @return Wait while option (from text) is selected. Return false if this not happens
     */
    @JDIAction
    boolean waitSelected(String name);

    /**
     * @param name Specify name using enum
     * @return Wait while option (from enum) is selected. Return false if this not happens
     */
    @JDIAction
    boolean waitSelected(TEnum name);

    /**
     * @return Get labels of all options
     */
    @JDIAction
    List<String> getOptions();

    default List<String> getNames() {
        return getOptions();
    }

    default List<String> getValues() {
        return getOptions();
    }

    /**
     * @return Get all options labels in one string separated with “; ”
     */
    @JDIAction
    default String getOptionsAsText() {
        return PrintUtils.print(getOptions());
    }
}
