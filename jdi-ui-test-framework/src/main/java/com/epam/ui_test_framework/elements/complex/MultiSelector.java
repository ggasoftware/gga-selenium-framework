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

public class MultiSelector<TEnum extends Enum> extends BaseSelector<TEnum> implements IMultiSelector<TEnum> {
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
    protected SetValue setValue() {
        return new SetValue(value -> selectListAction(value.split(", ")), super.setValue());
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
    public final void check(String... names) { clear(); select(names); }
    public final void check(TEnum... names) { clear(); select(names); }
    public final void check(int... indexes) { clear(); select(indexes); }
    public final void uncheck(String... names) { checkAll(); select(names); }
    public final void uncheck(TEnum... names) { checkAll(); select(names); }
    public final void uncheck(int... indexes) { checkAll(); select(indexes); }
    public final List<String> areSelected() {
        return doJActionResult("Are selected", () ->
                (List<String>) where(getNames(), this::waitSelectedAction));
    }
    public final boolean waitSelected(TEnum... names) {
        return waitSelected(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }
    public final boolean waitSelected(String... names) {
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
    public final boolean waitDeselected(TEnum... names) {
        return waitDeselected(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }
    public final boolean waitDeselected(String... names) {
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
    public void uncheckAll() { clear(); }

    public void checkAll() {
        foreach(where(getOptions(), label -> !waitSelectedAction(label)), this::selectAction);
    }

}
