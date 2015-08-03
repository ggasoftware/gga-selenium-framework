package com.epam.jdi_ui_tests.elements.composite;

import com.epam.jdi_ui_tests.elements.base.*;
import com.epam.jdi_ui_tests.elements.common.Button;
import com.epam.jdi_ui_tests.elements.interfaces.base.*;
import com.epam.jdi_ui_tests.elements.interfaces.common.IButton;
import com.epam.jdi_ui_tests.elements.interfaces.complex.IForm;
import com.epam.jdi_ui_tests.utils.map.MapArray;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.util.List;

import static com.epam.jdi_ui_tests.elements.page_objects.annotations.AnnotationsUtil.getElementName;
import static com.epam.jdi_ui_tests.settings.FrameworkSettings.asserter;
import static com.epam.jdi_ui_tests.utils.common.LinqUtils.*;
import static com.epam.jdi_ui_tests.utils.common.PrintUtils.*;
import static com.epam.jdi_ui_tests.utils.common.ReflectionUtils.*;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class Form<T> extends Element implements IForm<T> {
    public Form() { }
    public Form(By byLocator) { super(byLocator); }
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
                ISetValue seValueElement = (ISetValue) getFieldValue(element, this);
                setValueRule.invoke(fieldValue, val -> setValueAction(val, seValueElement));
            }
        });
    }
    public void fill(T entity) {
        fill(objToSetValue(entity));
    }

    private boolean namesEqual(String name1, String name2) {
        return name1.toLowerCase().replace(" ", "").equals(name2.toLowerCase().replace(" ", ""));
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
            () -> print(select(getFields(this, IHaveValue.class), field ->
                    ((IHaveValue) getFieldValue(field, this)).getValue())));
    }

    public final String getValue() { return setValue().getValue(); }
    public final void setValue(String value) { setValue().setValue(value); }

}
