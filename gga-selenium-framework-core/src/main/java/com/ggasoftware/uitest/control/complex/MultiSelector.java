package com.ggasoftware.uitest.control.complex;

import com.ggasoftware.uitest.control.base.Element;
import com.ggasoftware.uitest.control.base.ElementsList;
import com.ggasoftware.uitest.control.interfaces.IMultiSelector;
import com.ggasoftware.uitest.utils.common.LinqUtils;
import com.ggasoftware.uitest.utils.map.MapArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static com.ggasoftware.uitest.utils.common.LinqUtils.*;
import static com.ggasoftware.uitest.utils.common.PrintUtils.print;
import static com.ggasoftware.uitest.utils.common.WebDriverByUtils.fillByTemplateSilent;
import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.asserter;
import static java.lang.String.format;

/**
 * Created by roman.i on 03.10.2014.
 */

public class MultiSelector<TEnum extends Enum> extends ElementsList<TEnum> implements IMultiSelector<TEnum> {
    public MultiSelector() { super(); }
    public MultiSelector(By optionsNamesLocatorTemplate) { this(optionsNamesLocatorTemplate, null); }
    public MultiSelector(By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(allOptionsNamesLocator);
        this.optionsNamesLocatorTemplate = optionsNamesLocatorTemplate;
    }
    private By optionsNamesLocatorTemplate;

    @Override
    public WebElement getElement(String name) { return new Element(fillByTemplateSilent(optionsNamesLocatorTemplate, name)).getWebElement(); }
    protected void clickAction(String name) { getElement(name).click(); }
    protected void selectAction(String... names) { foreach(names, this::clickAction); }
    protected void clickByIndexAction(int index) { getElement(index).click(); }
    protected void selectAction(int... indexes) { for (int i : indexes) clickByIndexAction(i); }
    @Override
    protected String getValueAction() { return print(areSelected()); }
    protected boolean isSelectedAction(String value) { return getElement(value).isSelected(); }
    @Override
    protected MapArray<String, WebElement> getElementsAction() {
        if (!haveLocator()) {
            asserter.exception(format("Please specify 'allOptionsNamesLocator' locator for element '%s'", this.toString()));
            return null;
        }
        MapArray<String, WebElement> elements = super.getElementsAction();
        if (elements == null || elements.size() == 0) {
            asserter.exception(format("No labels found for element '%s'", this.toString()));
            return null;
        }
        return elements;
    }
    protected void setValueAction(String value) { selectAction(value.split(", "));}
    private String separator = ", ";
    public IMultiSelector<TEnum> setValuesSeparator(String separator) { this.separator = separator; return this; }

    public final void select(String... names) {
        doJAction(format("Select '%s'", print(names)), () -> selectAction(names));
    }
    public final void select(TEnum... names) {
        select(toStringArray(LinqUtils.select(names, this::getEnumValue)));
    }
    public final void select(int... indexes) {
        doJAction(format("Select '%s'", print(indexes)), () -> selectAction(indexes));
    }
    public final void selectOnly(String... names) { clear(); select(names); }
    public final void selectOnly(TEnum... names) { clear(); select(names); }
    public final void selectOnly(int... indexes) { clear(); select(indexes); }
    public final void deselectOnly(String... names) { selectAll(); select(names); }
    public final void deselectOnly(TEnum... names) { selectAll(); select(names); }
    public final void deselectOnly(int... indexes) { selectAll(); select(indexes); }
    public final List<String> areSelected() {
        return doJActionResult("Are selected", () -> {
            MapArray<String, WebElement> elements = getElements();
            if (elements == null) return null;
            return (List<String>) LinqUtils.where(elements.keys(), this::isSelectedAction);
        });
    }
    public final boolean areSelected(TEnum... names) {
        return areSelected(toStringArray(LinqUtils.select(names, this::getEnumValue)));
    }
    public final boolean areSelected(String... names) {
        return doJActionResult(format("Are deselected '%s'", print(names)), () -> {
            for (String name : names)
                if (!isSelectedAction(name))
                    return false;
            return true;
        });
    }
    public final List<String> areDeselected() {
        return doJActionResult("Are deselected", () -> {
            MapArray<String, WebElement> elements = getElements();
            if (elements == null) return null;
            return (List<String>) LinqUtils.where(elements.keys(), name -> !isSelectedAction(name));
        });
    }
    public final boolean areDeselected(TEnum... names) {
        return areDeselected(toStringArray(LinqUtils.select(names, this::getEnumValue)));
    }
    public final boolean areDeselected(String... names) {
        return doJActionResult(format("Are deselected '%s'", print(names)), () -> {
            for (String name : names)
                if (isSelectedAction(name))
                    return false;
            return true;
        });
    }

    public final String getLabelsAsText() { return print(getLabels()); }
    public void clear() {
        foreach(where(getLabels(), this::isSelectedAction), this::clickAction);
    }
    public void deselectAll() { clear();
    }

    public void selectAll() {
        foreach(where(getLabels(), label -> !isSelectedAction(label)), this::clickAction);
    }

    public final void setValue(String value) { doJAction("Set value", () -> setValueAction(value)); }
}
