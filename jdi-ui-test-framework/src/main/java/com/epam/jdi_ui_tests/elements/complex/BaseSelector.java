package com.epam.jdi_ui_tests.elements.complex;

import com.epam.jdi_ui_tests.elements.base.SelectElement;
import com.epam.jdi_ui_tests.elements.base.SetValue;
import org.openqa.selenium.By;

import java.util.List;

import static com.epam.jdi_ui_tests.utils.common.PrintUtils.print;
import static com.epam.jdi_ui_tests.utils.common.Timer.waitCondition;
import static com.epam.jdi_ui_tests.settings.FrameworkSettings.asserter;
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
    private TextList<TEnum> allLabels;

    protected void selectAction(String name) { getElement(name).click(); }
    protected void selectByIndexAction(int index) {
        if (index >= 0)
            getElement(getNames().get(index)).click();
    }
    protected boolean waitSelectedAction(String value) {
        return waitCondition(() -> getElement(value).isSelected());
    }
    protected SetValue setValue() { return new SetValue(this::selectAction, this);}

    protected List<String> getNames() {
        if (allLabels == null && elementsNames == null) {
            asserter.exception(format("Please specify 'allOptionsNamesLocator' locator or Enum to work with getAllElements method for element '%s'", this.toString()));
            return null;
        }
        List<String> names = (elementsNames != null)
                ? elementsNames
                : allLabels.getLabels();
        if (names == null || names.size() == 0) {
            asserter.exception(format("No labels found for element '%s'", this.toString()));
            return null;
        }
        return names;
    }
    public final void setValue(String value) { setValue().setValue(value); }
    public final List<String> getOptions() { return allLabels.getLabels(); }
    public final String getOptionsAsText() { return print(getOptions()); }

    public final String getValue() { return setValue().getValue(); }
}
