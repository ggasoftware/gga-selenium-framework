package com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Button;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Text;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IButton;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IText;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations.functions.Functions;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.ggasoftware.jdiuitests.core.settings.JDISettings.exception;
import static com.ggasoftware.jdiuitests.core.utils.common.LinqUtils.first;
import static com.ggasoftware.jdiuitests.core.utils.common.LinqUtils.select;
import static com.ggasoftware.jdiuitests.core.utils.common.ReflectionUtils.getFieldValue;
import static com.ggasoftware.jdiuitests.core.utils.common.ReflectionUtils.getFields;

/**
 * Created by Roman_Iovlev on 9/3/2015.
 */
public class GetElement {
    private BaseElement element;
    public GetElement(BaseElement element) { this.element = element; }

    public Button getButton(String buttonName) {
        List<Field> fields = getFields(element, IButton.class);
        if (fields.size() == 1)
            return (Button) getFieldValue(fields.get(0), element);
        Collection<Button> buttons = select(fields, f -> (Button) getFieldValue(f, element));
        Button button = first(buttons, b -> namesEqual(b.getName(), buttonName.toLowerCase().contains("button") ? buttonName : buttonName + "button"));
        if (button == null)
            throw exception("Can't find button '%s' for Element '%s'", buttonName, toString());
        return button;
    }

    public static boolean namesEqual(String name1, String name2) {
        return name1.toLowerCase().replace(" ", "").equals(name2.toLowerCase().replace(" ", ""));
    }

    public Button getButton(Functions funcName) {
        List<Field> fields = getFields(element, IButton.class);
        if (fields.size() == 1)
            return (Button) getFieldValue(fields.get(0), element);
        Collection<Button> buttons = select(fields, f -> (Button) getFieldValue(f, element));
        Button button = first(buttons, b -> b.function.equals(funcName));
        if (button == null) {
            String name = funcName.name;
            String buttonName = name.toLowerCase().contains("button") ? name : name + "button";
            button = first(buttons, b -> namesEqual(b.getName(), buttonName));
            if (button == null)
                throw exception("Can't find button '%s' for Element '%s'", name, toString());
        }
        return button;
    }

    public Text getTextElement() {
        Field textField = first(getClass().getDeclaredFields(), f -> (f.getType() == Text.class) || (f.getType() == IText.class));
        if (textField == null)
            throw exception("Can't find Text Element '%s'", toString());
        return (Text) getFieldValue(textField, element);
    }
}
