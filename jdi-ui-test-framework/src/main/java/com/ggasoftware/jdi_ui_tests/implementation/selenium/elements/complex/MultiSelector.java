package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex;

import com.ggasoftware.jdi_ui_tests.core.utils.common.EnumUtils;
import com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IMultiSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.exception;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.*;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.PrintUtils.print;

/**
 * Created by roman.i on 03.10.2014.
 */

public abstract class MultiSelector<TEnum extends Enum> extends BaseSelector<TEnum> implements IMultiSelector<TEnum> {
    public MultiSelector() { super(); }
    public MultiSelector(By optionsNamesLocator) { super(optionsNamesLocator); }
    public MultiSelector(By optionsNamesLocator, By allLabelsLocator) {
        super(optionsNamesLocator, allLabelsLocator);
    }

    protected void clearAction() {
        if (!haveLocator() && allLabels == null)
            throw exception("Can't clear options. No optionsNamesLocator and allLabelsLocator found");
        if (getLocator().toString().contains("%s"))
            throw exception("Can't clear options. Specify allLabelsLocator or fix optionsNamesLocator (should not contain %s)");
        if (allLabels != null) {
            clearElements(allLabels.getWebElements());
            return;
        }
        List<WebElement> els = getDriver().findElements(getLocator());
        if (els.size() == 1)
            new Select(new Element(getLocator()).getWebElement()).deselectAll();
        else
            clearElements(els);
    }
    private void clearElements(List<WebElement> els) {
        foreach(where(els, el -> isSelectedAction(el.getText())), WebElement::click);
    }
    protected boolean isSelectedAction(String name) { return areSelected().contains(name); }
    protected boolean isSelectedAction(int index) { return areSelected().contains(getNames().get(index));}

    protected void selectListAction(String... names) { foreach(names, this::selectAction); }
    protected void selectListAction(int... indexes) { for (int i : indexes) selectAction(i); }
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
        return actions.areSelected(this::getNames, this::isSelectedAction);
    }
    public final boolean waitSelected(TEnum... names) {
        return waitSelected(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }
    public final boolean waitSelected(String... names) {
        return actions.waitSelected(this::waitSelected, names);
    }
    public final List<String> areDeselected() {
        return actions.areDeselected(this::getNames, this::waitSelected);
    }
    public final boolean waitDeselected(TEnum... names) {
        return waitDeselected(toStringArray(LinqUtils.select(names, EnumUtils::getEnumValue)));
    }
    public final boolean waitDeselected(String... names) {
        return actions.waitDeselected(this::waitSelected, names);
    }

    public void clear() {
        invoker.doJAction("Clear Options", this::clearAction);
    }
    public void uncheckAll() { clear(); }

    public void checkAll() {
        foreach(where(getOptions(), label -> !waitSelected(label)), this::selectAction);
    }

}
