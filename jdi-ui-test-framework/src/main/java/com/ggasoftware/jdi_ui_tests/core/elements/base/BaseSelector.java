package com.ggasoftware.jdi_ui_tests.core.elements.base;

import com.ggasoftware.jdi_ui_tests.core.elements.template.base.SelectElement;
import com.ggasoftware.jdi_ui_tests.core.elements.template.complex.TextList;
import com.ggasoftware.jdi_ui_tests.utils.common.Timer;

import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.elements.base.SetValueRule.setValueRule;
import static com.ggasoftware.jdi_ui_tests.utils.common.PrintUtils.print;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public abstract class BaseSelector<TEnum extends Enum> extends TemplatesList<SelectElement, TEnum> {
    protected TextList<TEnum> allLabels;

    // Actions
    protected void selectAction(String name) { getElement(name).click(); }
    protected void selectByIndexAction(int index) {
        if (index >= 0)
            getElement(getNames().get(index)).click();
    }
    protected boolean waitSelectedAction(String value) {
        return Timer.waitCondition(() -> getElement(value).isSelected());
    }
    protected void setValueAction(String value) { selectAction(value); }

    // Methods
    protected List<String> getNames() {
        if (allLabels == null && elementsNames == null)
            throw asserter.exception(format("Please specify 'allOptionsNamesLocator' locator or Enum to work with getAllElements method for element '%s'", this.toString()));
        List<String> names = (elementsNames != null)
                ? elementsNames
                : allLabels.getLabels();
        if (names == null || names.size() == 0)
            throw asserter.exception(format("No labels found for element '%s'", this.toString()));
        return names;
    }
    public final List<String> getOptions() { return allLabels.getLabels(); }
    public final String getOptionsAsText() { return print(getOptions()); }

    public final void setValue(String value) { doJAction("Set value", () -> setValueRule(value, this::setValueAction)); }
}
