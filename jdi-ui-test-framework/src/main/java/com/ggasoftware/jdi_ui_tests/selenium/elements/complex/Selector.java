package com.ggasoftware.jdi_ui_tests.selenium.elements.complex;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.ISelector;
import com.ggasoftware.jdi_ui_tests.selenium.elements.base.SelectElement;
import com.ggasoftware.jdi_ui_tests.selenium.elements.base.SetValue;
import org.openqa.selenium.By;

import java.util.List;

import static com.ggasoftware.jdi_ui_tests.utils.common.EnumUtils.getEnumValue;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.first;
import static java.lang.String.format;

/**
 * Created by roman.i on 03.10.2014.
 */

public class Selector<TEnum extends Enum> extends BaseSelector<TEnum> implements ISelector<TEnum> {
    public Selector() { super(); }
    public Selector(By optionsNamesLocatorTemplate) { super(optionsNamesLocatorTemplate); }
    public Selector(By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(optionsNamesLocatorTemplate, allOptionsNamesLocator);
    }
    public Selector(By optionsNamesLocatorTemplate, TEnum enumMember) {
        super(optionsNamesLocatorTemplate, enumMember);
    }
    @Override
    protected SetValue setValue() { return new SetValue(this::selectAction, this::isSelected); }

    public final void select(String name) { doJAction(format("Select '%s'", name), () -> setValueRule(name, this::selectAction)); }
    public final void select(TEnum name) { select(getEnumValue(name)); }
    public final void select(int index) {
        doJAction(format("Select '%s'", index), () -> selectByIndexAction(index)); }
    public final String isSelected() {
        return doJActionResult("Is Selected", () -> {
            List<SelectElement> elements = getElementsList();
            if (elements == null) return null;
            SelectElement selectedElement = first(elements, SelectElement::isSelected);
            return (selectedElement != null) ? selectedElement.getText() : null;
        });
    }
    public final boolean waitSelected(TEnum name) { return waitSelected(getEnumValue(name)); }
    public final boolean waitSelected(String name) {
        return doJActionResult(format("Wait is '%s' selected", name), () -> waitSelectedAction(name));
    }
}
