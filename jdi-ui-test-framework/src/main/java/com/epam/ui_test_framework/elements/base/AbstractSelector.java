package com.epam.ui_test_framework.elements.base;

import com.epam.ui_test_framework.elements.complex.TextList;
import org.openqa.selenium.By;

import java.util.List;

import static com.epam.ui_test_framework.utils.common.PrintUtils.print;
import static com.epam.ui_test_framework.utils.common.Timer.waitCondition;
import static com.epam.ui_test_framework.settings.FrameworkSettings.asserter;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public abstract class AbstractSelector<TEnum extends Enum> extends TemplatesList<SelectElement, TEnum>{
    public AbstractSelector() { super(); }
    public AbstractSelector(By optionsNamesLocatorTemplate) {
        super(optionsNamesLocatorTemplate, new SelectElement(optionsNamesLocatorTemplate));
    }
    public AbstractSelector(By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(optionsNamesLocatorTemplate, new SelectElement(optionsNamesLocatorTemplate));
        allLabels = new TextList<>(allOptionsNamesLocator);
    }
    public AbstractSelector(By optionsNamesLocatorTemplate, TEnum enumMember) {
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
