package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.ISelector;
import org.openqa.selenium.By;

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
    public Selector(By optionsNamesLocatorTemplate, TEnum enumMember) {
        super(optionsNamesLocatorTemplate, enumMember);
    }
    protected String getValueAction() { return isSelected(); }

    public final void select(String name) { actions.select(name, this::selectAction); }
    public final void select(TEnum name) { select(getEnumValue(name)); }
    public final void select(int index) { actions.select(index, this::selectByIndexAction); }
    public final String isSelected() { return actions.isSelectedList(this::getElementsList); }
    public final boolean waitSelected(TEnum name) { return waitSelected(getEnumValue(name)); }
    public final boolean waitSelected(String name) {
        return actions.waitSelected(name, this::waitSelectedAction);
    }
    public final String getValue() { return actions.getValue(this::getValueAction); }
}
