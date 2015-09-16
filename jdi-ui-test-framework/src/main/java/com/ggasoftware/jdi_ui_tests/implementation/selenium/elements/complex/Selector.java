package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.ISelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.EnumUtils.getEnumValue;

/**
 * Created by roman.i on 03.10.2014.
 */

public class Selector<TEnum extends Enum> extends BaseSelector<TEnum> implements ISelector<TEnum> {
    public Selector() { super(); }
    public Selector(By optionsNamesLocatorTemplate) { super(optionsNamesLocatorTemplate); }
    public Selector(By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(optionsNamesLocatorTemplate, allOptionsNamesLocator);
    }
    public final void select(String name) { actions.select(name, this::selectAction); }
    public final void select(TEnum name) { select(getEnumValue(name)); }
    public final void select(int index) { actions.select(index, this::selectAction); }
    public final String getSelected() { return actions.getSelected(this::getSelectedAction); }
    public final int getSelectedIndex() { return actions.getSelectedIndex(this::getSelectedIndexAction); }

    protected boolean isSelectedAction(String name) {
        return getSelectedAction().equals(name);
    }
    protected boolean isSelectedAction(int index) {
        return getSelectedIndexAction() == index;
    }
    protected String getValueAction() {return getSelected(); }
    protected String getSelectedAction() {
        if (allLabels == null && haveLocator() && !getLocator().toString().contains("%s")) {
            List<WebElement> els = getDriver().findElements(getLocator());
            if (els.size() == 1)
                return new Select(new Element(getLocator()).getWebElement())
                        .getFirstSelectedOption().getText();
        }
        throw asserter.exception("Can't get Selected options. Override getSelectedAction or place locator to <select> tag");
    }
    protected int getSelectedIndexAction() {
        if (allLabels == null && haveLocator() && !getLocator().toString().contains("%s")) {
            List<WebElement> els = getDriver().findElements(getLocator());
            if (els.size() == 1) {
                List<WebElement> options = new Select(new Element(getLocator()).getWebElement()).getAllSelectedOptions();
                for (int i = 0; i < options.size(); i++)
                    if (options.get(i).isSelected())
                        return i;
                return -1;
            }
        }
        throw asserter.exception("Can't get Selected options. Override getSelectedIndexAction or place locator to <select> tag");
    }
}
