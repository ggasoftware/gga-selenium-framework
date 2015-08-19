package com.ggasoftware.jdi_ui_tests.core.elements.template.composite;

import com.ggasoftware.jdi_ui_tests.core.elements.template.base.Element;
import com.ggasoftware.jdi_ui_tests.core.elements.template.common.Button;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.*;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.common.IButton;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.IForm;
import com.ggasoftware.jdi_ui_tests.utils.map.MapArray;

import java.lang.reflect.Field;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.elements.base.SetValueRule.setValueRule;
import static com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.AnnotationsUtil.getElementName;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.*;
import static com.ggasoftware.jdi_ui_tests.utils.common.PrintUtils.*;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.*;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class Form<T> extends Element implements IForm<T> {
    // Actions
    protected void setValueAction(String value) { submit(parseObjectAsString(value)); }
    protected String getValueAction() { return print(select(getFields(this, IHasValue.class), field ->
            ((IHasValue) getFieldValue(field, this)).getValue())); }

    // Methods
    public void fill(MapArray<String, String> objStrings) {
        foreach(getFields(this, ISetValue.class), element -> {
            String fieldValue = objStrings.first(name ->
                    namesEqual(name, getElementName(element)));
            if (fieldValue != null) {
                ISetValue setValueElement = (ISetValue) getFieldValue(element, this);
                setValueRule.invoke(fieldValue, setValueElement::setValue);
            }
        });
    }
    public void fill(T entity) { fill(objToSetValue(entity)); }
    private Button getSubmitButton() {
        List<Field> fields = getFields(this, IButton.class);
        if (fields.size() != 1)
            throw asserter.exception((fields.size() == 0)
                ? format("Can't find any buttons on form '%s.", toString())
                : format("Form '%s' have more than 1 button. Use submit(entity, buttonName) for this case instead", toString()));
        return (Button) getFieldValue(fields.get(0), this);
    }
    public void submit(MapArray<String, String> objStrings) {
        fill(objStrings);
        getSubmitButton().click();
    }
    public void submit(T entity) { submit(objToSetValue(entity)); }
    public void submit(T entity, String buttonName) {
        fill(objToSetValue(entity));
        getButton(buttonName).click();
    }
    public void submit(T entity, Enum buttonName) {
        fill(objToSetValue(entity));
        getButton(buttonName.toString().toLowerCase()).click();
    }

    public final String getValue() { return doJActionResult("Get value", this::getValueAction); }
    public final void setValue(String value) { doJAction("Set value", () -> setValueRule(value, this::setValueAction)); }

}
