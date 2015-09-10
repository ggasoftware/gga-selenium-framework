package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.SelectElement;
import com.ggasoftware.jdi_ui_tests.core.utils.common.Timer;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.PrintUtils.print;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
abstract class BaseSelector<TEnum extends Enum> extends TemplatesList<SelectElement, TEnum> {
    public BaseSelector() { super(); }
    public BaseSelector(By optionsNamesLocatorTemplate) {
        super(optionsNamesLocatorTemplate, new SelectElement(optionsNamesLocatorTemplate));
    }
    public BaseSelector(By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(optionsNamesLocatorTemplate, new SelectElement(optionsNamesLocatorTemplate));
        allLabels = new TextList<>(allOptionsNamesLocator);
    }
    public BaseSelector(By optionsNamesLocatorTemplate, TEnum enumMember) {
        super(optionsNamesLocatorTemplate, new SelectElement(optionsNamesLocatorTemplate), enumMember);
    }
    protected TextList<TEnum> allLabels;
    protected SelectElement getDefaultElement(By locator) { return new SelectElement(locator); }

    protected void selectAction(String name) {
    if (haveLocator() && getLocator().toString().contains("%s"))
        getElement(name).click();
    else
        new Select(new Element(getLocator()).getWebElement()).selectByValue(name);
    }
    protected void selectByIndexAction(int index) {
        if (index >= 0)
            getElement(getNames().get(index)).click();
    }
    protected boolean waitSelectedAction(String value) {
        return Timer.waitCondition(() -> getElement(value).isSelected());
    }
    protected boolean isSelectedAction(String value) {
        return getElement(value).isSelected();
    }
    protected void setValueAction(String value) { selectAction(value); }

    protected List<String> getNames() {
        if (allLabels == null && elementsNames == null)
            throw asserter.exception(format("Please specify 'allOptionsNamesLocator' locator or Enum to work with getAllElements method for webElement '%s'"
                    , this.toString()));
        List<String> names = (elementsNames != null)
                ? elementsNames
                : allLabels.getLabels();
        if (names == null || names.size() == 0)
            throw asserter.exception(format("No labels found for webElement '%s'", this.toString()));
        return names;
    }
    public final void setValue(String value) { actions.setValue(value, this::setValueAction); }
    public final List<String> getOptions() { return getNames(); }
    public final String getOptionsAsText() { return print(getOptions()); }
}
