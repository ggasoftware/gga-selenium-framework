package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.ISetValue;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations.JDIAction;
import com.ggasoftware.jdiuitests.core.utils.common.PrintUtils;

import java.util.List;

import static com.ggasoftware.jdiuitests.core.utils.common.PrintUtils.print;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface ISelector<TEnum extends Enum> extends IBaseElement, ISetValue {
    /** Select Element with name (use text) from list */
    @JDIAction
    void select(String name);
    /** Select Element with name (use enum) from list */
    @JDIAction
    void select(TEnum name);
    /** Select Element with name (use index) from list */
    @JDIAction
    void select(int index);
    /** Get name of the selected Element */
    @JDIAction
    String getSelected();
    /** Get index of the selected Element */
    @JDIAction
    int getSelectedIndex();
    /** Is option selected? */
    @JDIAction
    boolean isSelected(String name);
    /** Is option selected? */
    @JDIAction
    boolean isSelected(TEnum name);
    /** Wait while option (from text) is selected. Return false if this not happens */
    @JDIAction
    boolean waitSelected(String name);
    /** Wait while option (from enum) is selected. Return false if this not happens */
    @JDIAction
    boolean waitSelected(TEnum name);
    /** Get labels of all options */
    @JDIAction
    List<String> getOptions();
    default List<String> getNames() { return getOptions(); }
    default List<String> getValues() { return getOptions(); }
    /** Get all options labels in one string separated with “; ” */
    @JDIAction
    default String getOptionsAsText() { return PrintUtils.print(getOptions()); }
}
