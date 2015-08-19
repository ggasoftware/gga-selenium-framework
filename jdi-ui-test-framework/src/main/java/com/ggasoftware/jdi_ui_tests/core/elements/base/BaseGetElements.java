package com.ggasoftware.jdi_ui_tests.core.elements.base;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.common.IText;
import com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.functions.Functions;
import com.ggasoftware.jdi_ui_tests.core.elements.template.common.Button;
import com.ggasoftware.jdi_ui_tests.core.elements.template.common.Text;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.first;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.select;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.getFieldValue;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.getFields;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 8/17/2015.
 */
public abstract class BaseGetElements {
    protected final boolean namesEqual(String name1, String name2) {
        return name1.toLowerCase().replace(" ", "").equals(name2.toLowerCase().replace(" ", ""));
    }

    protected final Button getButton(Functions funcName) {
        List<Field> fields = getFields(this, Button.class);
        if (fields.size() == 1)
            return (Button) getFieldValue(fields.get(0), this);
        Collection<Button> buttons = select(fields, f -> (Button) getFieldValue(f, this));
        Button button = first(buttons, b -> b.function.equals(funcName));
        if (button == null) {
            String buttonName = funcName.name.toLowerCase().contains("button") ? funcName.name : funcName.name + "button";
            button = first(buttons, b -> namesEqual(b.getName(), buttonName));
            if (button == null)
                throw asserter.exception(format("Can't find button '%s' for element '%s'", funcName, toString()));
        }
        return button;
    }

    protected final Text getTextElement() {
        Field textField = first(getClass().getDeclaredFields(), f -> (f.getType() == Text.class) || (f.getType() == IText.class));
        if (textField == null)
            throw asserter.exception(format("Can't find Text element '%s'", toString()));
        return (Text) getFieldValue(textField, this);
    }
}
