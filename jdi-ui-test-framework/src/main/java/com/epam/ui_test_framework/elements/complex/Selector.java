package com.epam.ui_test_framework.elements.complex;

import com.epam.ui_test_framework.elements.base.AbstractSelector;
import com.epam.ui_test_framework.elements.base.SelectElement;
import com.epam.ui_test_framework.elements.interfaces.base.ISelector;
import org.openqa.selenium.By;

import java.util.List;

import static com.epam.ui_test_framework.utils.common.EnumUtils.getEnumValue;
import static com.epam.ui_test_framework.utils.common.LinqUtils.*;
import static java.lang.String.format;

/**
 * Created by roman.i on 03.10.2014.
 */

public class Selector<TEnum extends Enum> extends AbstractSelector<TEnum> implements ISelector<TEnum> {
    public Selector() { super(); }
    public Selector(By optionsNamesLocatorTemplate) { super(optionsNamesLocatorTemplate); }
    public Selector(By optionsNamesLocatorTemplate, By allOptionsNamesLocator) {
        super(optionsNamesLocatorTemplate, allOptionsNamesLocator);
    }
    public Selector(By optionsNamesLocatorTemplate, TEnum enumMember) {
        super(optionsNamesLocatorTemplate, enumMember);
    }
    @Override
    protected void setValueAction(String value) { selectAction(value); }

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
        return doJActionResult(format("Wait Selected + '%s'", name), () -> waitSelectedAction(name));
    }
    protected String getValueAction() { return isSelected(); }

}
