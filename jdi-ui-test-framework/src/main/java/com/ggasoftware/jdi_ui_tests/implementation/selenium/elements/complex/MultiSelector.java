package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex;

import com.ggasoftware.jdi_ui_tests.core.utils.common.EnumUtils;
import com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IMultiSelector;
import org.openqa.selenium.By;

import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.*;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.PrintUtils.print;

/**
 * Created by roman.i on 03.10.2014.
 */

public abstract class MultiSelector<TEnum extends Enum> extends BaseSelector<TEnum> implements IMultiSelector<TEnum> {
    public MultiSelector() { super(); }
    public MultiSelector(By optionsNamesLocatorTemplate) { super(optionsNamesLocatorTemplate); }
    public MultiSelector(By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(optionsNamesLocatorTemplate, allOptionsNamesLocator);
    }
    public MultiSelector(By optionsNamesLocatorTemplate, TEnum enumMember) {
        super(optionsNamesLocatorTemplate, enumMember);
    }

    protected void selectListAction(String... names) { foreach(names, this::selectAction); }
    protected void selectListAction(int... indexes) { for (int i : indexes) selectByIndexAction(i); }
    protected boolean waitSelectedAction(String value) { return getElement(value).isSelected(); }
    @Override
    protected void setValueAction(String value) { selectListAction(value.split(", ")); }
    protected String getValueAction() { return print(areSelected()); }

    private String separator = ", ";
    public IMultiSelector<TEnum> setValuesSeparator(String separator) { this.separator = separator; return this; }

    public final void select(String... names) {
        actions.select(this::selectListAction, names);
    }
    public final void select(TEnum... names) {
        select(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }
    public final void select(int... indexes) {
        actions.select(this::selectListAction, indexes);
    }
    public final void check(String... names) { clear(); select(names); }
    public final void check(TEnum... names) { clear(); select(names); }
    public final void check(int... indexes) { clear(); select(indexes); }
    public final void uncheck(String... names) { checkAll(); select(names); }
    public final void uncheck(TEnum... names) { checkAll(); select(names); }
    public final void uncheck(int... indexes) { checkAll(); select(indexes); }
    public final List<String> areSelected() {
        return actions.areSelected(this::getNames, this::waitSelectedAction);
    }
    public final boolean waitSelected(TEnum... names) {
        return waitSelected(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }
    public final boolean waitSelected(String... names) {
        return actions.waitSelected(this::waitSelectedAction, names);
    }
    public final List<String> areDeselected() {
        return actions.areDeselected(this::getNames, this::waitSelectedAction);
    }
    public final boolean waitDeselected(TEnum... names) {
        return waitDeselected(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }
    public final boolean waitDeselected(String... names) {
        return actions.waitDeselected(this::waitSelectedAction, names);
    }

    public void clear() {
        foreach(where(getOptions(), this::waitSelectedAction), this::selectAction);
    }
    public void uncheckAll() { clear(); }

    public void checkAll() {
        foreach(where(getOptions(), label -> !waitSelectedAction(label)), this::selectAction);
    }
    public final String getValue() { return actions.getValue(this::getValueAction); }

}
