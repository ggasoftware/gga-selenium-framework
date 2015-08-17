package com.ggasoftware.jdi_ui_tests.core.elements.base;

import com.ggasoftware.jdi_ui_tests.core.elements.common.AButton;
import com.ggasoftware.jdi_ui_tests.core.elements.common.AText;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.common.IButton;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.common.IText;
import com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.functions.Functions;
import com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.first;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.select;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.getFieldValue;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 8/17/2015.
 */
public class BaseGetElements {
    protected AButton getButton(String buttonName) {
        List<Field> fields = ReflectionUtils.getFields(this, IButton.class);
        if (fields.size() == 1)
            return (AButton) getFieldValue(fields.get(0), this);
        Collection<AButton> buttons = select(fields, f -> (AButton) getFieldValue(f, this));
        AButton button = first(buttons, b -> namesEqual(b.getName(), buttonName.toLowerCase().contains("button") ? buttonName : buttonName + "button"));
        if (button == null)
            throw asserter.exception(format("Can't find button '%s' for element '%s'", buttonName, toString()));
        return button;
    }

    protected boolean namesEqual(String name1, String name2) {
        return name1.toLowerCase().replace(" ", "").equals(name2.toLowerCase().replace(" ", ""));
    }

    protected AButton getButton(Functions funcName) {
        List<Field> fields = ReflectionUtils.getFields(this, IButton.class);
        if (fields.size() == 1)
            return (AButton) getFieldValue(fields.get(0), this);
        Collection<AButton> buttons = select(fields, f -> (AButton) getFieldValue(f, this));
        AButton button = first(buttons, b -> b.function.equals(funcName));
        if (button == null)
            throw asserter.exception(format("Can't find button '%s' for element '%s'", funcName, toString()));
        return button;
    }

    protected AText getTextElement() {
        Field textField = first(getClass().getDeclaredFields(), f -> (f.getType() == AText.class) || (f.getType() == IText.class));
        if (textField == null)
            throw asserter.exception(format("Can't find Text element '%s'", toString()));
        return (AText) getFieldValue(textField, this);
    }
}
