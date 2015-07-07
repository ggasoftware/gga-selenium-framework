package com.ggasoftware.uitest.control.complex;

import com.ggasoftware.uitest.control.base.Element;
import com.ggasoftware.uitest.control.interfaces.ISelector;
import com.ggasoftware.uitest.control.base.ElementsList;
import com.ggasoftware.uitest.utils.map.MapArray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.ggasoftware.uitest.utils.common.LinqUtils.first;
import static com.ggasoftware.uitest.utils.common.PrintUtils.print;
import static com.ggasoftware.uitest.utils.common.WebDriverByUtils.fillByTemplateSilent;
import static com.ggasoftware.uitest.utils.settings.FrameworkSettings.asserter;
import static java.lang.String.format;

/**
 * Created by roman.i on 03.10.2014.
 */

public class Selector<TEnum extends Enum> extends ElementsList<TEnum> implements ISelector<TEnum> {
    public Selector() { super(); }
    public Selector(By optionsNamesLocatorTemplate) { this(optionsNamesLocatorTemplate, null); }
    public Selector(By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(allOptionsNamesLocator);
        this.optionsNamesLocatorTemplate = optionsNamesLocatorTemplate;
    }
    private By optionsNamesLocatorTemplate;

    @Override
    public WebElement getElement(String name) { return new Element(fillByTemplateSilent(optionsNamesLocatorTemplate, name)).getWebElement(); }
    protected void selectAction(String name) { getElement(name).click(); }
    protected void selectByIndexAction(int index) { getElement(index).click(); }
    @Override
    protected String getValueAction() { return getElement(isSelected()).getText(); }
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
    protected void setValueAction(String value) {selectAction(value); }

    public final void select(String name) {
        doJAction(format("Select '%s'", name), () -> selectAction(name)); }
    public final void select(TEnum name) { select(getEnumValue(name)); }
    public final void select(int index) {
        doJAction(format("Select '%s'", index), () -> selectByIndexAction(index)); }
    public final String isSelected() {
        return doJActionResult("Is Selected", () -> {
            MapArray<String, WebElement> elements = getElements();
            if (elements == null) return null;
            return first(elements.keys(), this::isSelectedAction);
        });
    }
    public final void setValue(String value) { doJAction("Set value", () -> setValueAction(value)); }
    public final boolean isSelected(TEnum name) {
        return isSelected(getEnumValue(name));
    }
    public final boolean isSelected(String name) { return doJActionResult(format("Is Selected + '%s", name), () -> isSelectedAction(name)); }
    public final List<String> getOptions() { return getLabels(); }
    public final String getOptionsAsText() { return print(getLabels()); }

}
