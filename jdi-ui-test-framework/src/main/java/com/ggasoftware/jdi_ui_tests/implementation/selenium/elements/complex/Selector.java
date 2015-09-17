package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.ISelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.EnumUtils.getEnumValue;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.first;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.firstIndex;

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

    protected final boolean isSelectedAction(String name) {
        return getSelectedAction().equals(name);
    }
    protected final boolean isSelectedAction(int index) {
        return getSelectedIndexAction() == index;
    }
    protected String getValueAction() {return getSelected(); }
    protected boolean isSelectedAction(WebElement el) {
        return el.isSelected();
    }
    protected final String getSelectedAction() {
        if (allLabels != null)
            return getSelected(allLabels.getWebElements());
        if (getLocator().toString().contains("%s"))
            throw asserter.exception("Can't get Selected options. Override getSelectedAction or place locator to <select> tag");
        List<WebElement> els = getDriver().findElements(getLocator());
        return getSelected(els.size() == 1
                ? new Select(new Element(getLocator()).getWebElement()).getOptions()
                : els);
    }
    private String getSelected(List<WebElement> els) {
        WebElement element = first(els, this::isSelectedAction);
        if (element == null)
            throw asserter.exception("No elements selected");
        return element.getText();
    }
    protected final int getSelectedIndexAction() {
        if (allLabels != null) {
            return getSelectedIndex(allLabels.getWebElements());
        }
        if (getLocator().toString().contains("%s"))
            throw asserter.exception("Can't get Selected options. Override getSelectedAction or place locator to <select> tag");
        List<WebElement> els = getDriver().findElements(getLocator());
        return getSelectedIndex(els.size() == 1
                ? new Select(new Element(getLocator()).getWebElement()).getOptions()
                : els);
    }
    private int getSelectedIndex(List<WebElement> els) {
        return firstIndex(els, this::isSelectedAction) + 1;
    }
}
