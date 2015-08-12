package com.ggasoftware.jdi_ui_tests.selenium.elements.composite;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IHasValue;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.ISetValue;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.common.IButton;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.IForm;
import com.ggasoftware.jdi_ui_tests.selenium.elements.base.Element;
import com.ggasoftware.jdi_ui_tests.selenium.elements.base.SetValue;
import com.ggasoftware.jdi_ui_tests.selenium.elements.common.Button;
import com.ggasoftware.jdi_ui_tests.utils.map.MapArray;

import java.lang.reflect.Field;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.AnnotationsUtil.getElementName;
import static com.ggasoftware.jdi_ui_tests.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.foreach;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.select;
import static com.ggasoftware.jdi_ui_tests.utils.common.PrintUtils.*;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.getFieldValue;
import static com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils.getFields;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class Form<T> extends Element implements IForm<T> {
    private boolean fillEmptyValue = false;
    public Form fillEmptyValue() { fillEmptyValue = true; return this; }

    protected void setValueAction(String text, ISetValue element) {
        element.setValue(text);
    }

    protected void fill(MapArray<String, String> objStrings) {
        foreach(getFields(this, ISetValue.class), element -> {
            String fieldValue = objStrings.first(name ->
                    namesEqual(name, getElementName(element)));
            if (fieldValue != null) {
                ISetValue setValueElement = (ISetValue) getFieldValue(element, this);
                setValueRule.invoke(fieldValue, val -> setValueAction(val, setValueElement));
            }
        });
    }
    public void fill(T entity) {
        fill(objToSetValue(entity));
    }


    private Button getSubmitButton() {
        List<Field> fields = getFields(this, IButton.class);
        switch (fields.size()) {
            case 0:
                asserter.exception(format("Can't find any buttons on form '%s.", toString()));
                return null;
            case 1:
                return (Button) getFieldValue(fields.get(0), this);
            default:
                asserter.exception(format("Form '%s' have more than 1 button. Use submit(entity, buttonName) for this case instead", toString()));
                return null;
        }
    }
    protected void submit(MapArray<String, String> objStrings) {
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
    protected SetValue setValue() { return new SetValue(
            value -> submit(parseObjectAsString(value)),
            () -> print(select(getFields(this, IHasValue.class), field ->
                    ((IHasValue) getFieldValue(field, this)).getValue())));
    }

    public final String getValue() { return setValue().getValue(); }
    public final void setValue(String value) { setValue().setValue(value); }

}
