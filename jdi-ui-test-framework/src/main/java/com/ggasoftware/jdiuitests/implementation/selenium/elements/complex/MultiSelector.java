package com.ggasoftware.jdiuitests.implementation.selenium.elements.complex;

import com.ggasoftware.jdiuitests.core.utils.common.EnumUtils;
import com.ggasoftware.jdiuitests.core.utils.common.LinqUtils;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.apiInteract.GetElementModule;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IMultiSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.ggasoftware.jdiuitests.core.settings.JDISettings.exception;
import static com.ggasoftware.jdiuitests.core.utils.common.LinqUtils.*;
import static com.ggasoftware.jdiuitests.core.utils.common.PrintUtils.print;
import static com.ggasoftware.jdiuitests.core.utils.common.WebDriverByUtils.fillByTemplate;

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
        if (!haveLocator() && allLabels() == null)
            throw exception("Can't clear options. No optionsNamesLocator and allLabelsLocator found");
        if (getLocator().toString().contains("%s"))
            throw exception("Can't clear options. Specify allLabelsLocator or fix optionsNamesLocator (should not contain '%s')");
        if (allLabels() != null) {
            clearElements(allLabels().getWebElements());
            return;
        }
        List<WebElement> els = getAvatar().searchAll().getElements();
        if (els.size() == 1)
            getSelector().deselectAll();
        else
            clearElements(els);
    }
    private void clearElements(List<WebElement> els) {
        foreach(where(els, el -> isSelectedAction(el.getText())), WebElement::click);
    }
    protected WebElement getElement(String name) {
        List<WebElement> els = null;
        if (!haveLocator() && allLabels() == null)
            throw exception("Can't get option. No optionsNamesLocator and allLabelsLocator found");
        if (getLocator().toString().contains("%s"))
            els = new GetElementModule(fillByTemplate(getLocator(), name), getAvatar().context, this).getElements();
        if (allLabels() != null)
            els = getElement(allLabels().getWebElements(), name);
        if (els == null)
            els = getAvatar().searchAll().getElements();
        if (els.size() == 1)
            els = getSelector().getOptions();
        if (els == null)
            throw exception("Can't get option. No optionsNamesLocator and allLabelsLocator found");
        else
            els = getElement(els, name);
        if (els == null || els.size() != 1)
            throw exception("Can't get option. No optionsNamesLocator and allLabelsLocator found");
        return els.get(0);
    }
    private List<WebElement> getElement(List<WebElement> els, String name) {
        return where(els, el -> el.getText().equals(name));
    }
    protected WebElement getElement(int index) {
        if (!haveLocator() && allLabels() == null)
            throw exception("Can't get option. No optionsNamesLocator and allLabelsLocator found");
        if (getLocator().toString().contains("%s"))
            throw exception("Can't get options. Specify allLabelsLocator or fix optionsNamesLocator (should not contain '%s')");
        if (allLabels() != null)
            return getElement(allLabels().getWebElements(), index);
        List<WebElement> els = getAvatar().searchAll().getElements();
        return getElement(els.size() == 1
                ? getSelector().getOptions()
                : els, index);
    }
    private WebElement getElement(List<WebElement> els, int index) {
        if (index <= 0)
            throw exception("Can't get option with index '%s'. Index should be 1 or more", index);
        if (index > els.size())
            throw exception("Can't get option with index '%s'. Found only %s options", index, els.size());
        return els.get(index - 1);
    }
    protected boolean isSelectedAction(String name) { return isSelectedAction(getElement(name)); }
    protected boolean isSelectedAction(int index) { return isSelectedAction(getElement(index));}

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

    public void checkAll() {
        foreach(where(getOptions(), label -> !isSelectedAction(label)), this::selectAction);
    }

}
