package com.epam.ui_test_framework.elements.complex;

import com.epam.ui_test_framework.elements.base.*;
import com.epam.ui_test_framework.elements.interfaces.base.IMultiSelector;
import com.epam.ui_test_framework.utils.common.EnumUtils;
import com.epam.ui_test_framework.utils.common.LinqUtils;
import org.openqa.selenium.By;

import java.util.List;

import static com.epam.ui_test_framework.utils.common.LinqUtils.*;
import static com.epam.ui_test_framework.utils.common.PrintUtils.print;
import static java.lang.String.format;

/**
 * Created by roman.i on 03.10.2014.
 */

public class MultiSelector<TEnum extends Enum> extends AbstractSelector<TEnum> implements IMultiSelector<TEnum> {
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
    protected void setValueAction(String value) {
        if (value == null) return;
        selectListAction(value.split(", "));
    }
    private String separator = ", ";
    public IMultiSelector<TEnum> setValuesSeparator(String separator) { this.separator = separator; return this; }

    public final void select(String... names) {
        doJAction(format("Select '%s'", print(names)), () -> selectListAction(names));
    }
    public final void select(TEnum... names) {
        select(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }
    public final void select(int... indexes) {
        doJAction(format("Select '%s'", print(indexes)), () -> selectListAction(indexes));
    }
    public final void selectOnly(String... names) { clear(); select(names); }
    public final void selectOnly(TEnum... names) { clear(); select(names); }
    public final void selectOnly(int... indexes) { clear(); select(indexes); }
    public final void deselectOnly(String... names) { selectAll(); select(names); }
    public final void deselectOnly(TEnum... names) { selectAll(); select(names); }
    public final void deselectOnly(int... indexes) { selectAll(); select(indexes); }
    public final List<String> areSelected() {
        return doJActionResult("Are selected", () ->
                (List<String>) where(getNames(), this::waitSelectedAction));
    }
    public final boolean areSelected(TEnum... names) {
        return areSelected(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }
    public final boolean areSelected(String... names) {
        return doJActionResult(format("Are deselected '%s'", print(names)), () -> {
            for (String name : names)
                if (!waitSelectedAction(name))
                    return false;
            return true;
        });
    }
    public final List<String> areDeselected() {
        return doJActionResult("Are deselected", () ->
                (List<String>) where(getNames(), name -> !waitSelectedAction(name)));
    }
    public final boolean areDeselected(TEnum... names) {
        return areDeselected(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }
    public final boolean areDeselected(String... names) {
        return doJActionResult(format("Are deselected '%s'", print(names)), () -> {
            for (String name : names)
                if (waitSelectedAction(name))
                    return false;
            return true;
        });
    }

    public void clear() {
        foreach(where(getOptions(), this::waitSelectedAction), this::selectAction);
    }
    public void deselectAll() { clear(); }

    public void selectAll() {
        foreach(where(getOptions(), label -> !waitSelectedAction(label)), this::selectAction);
    }

}
