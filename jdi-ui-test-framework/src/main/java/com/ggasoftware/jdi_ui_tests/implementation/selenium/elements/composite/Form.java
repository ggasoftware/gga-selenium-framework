package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite;

import com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JActionTT;
import com.ggasoftware.jdi_ui_tests.core.utils.map.MapArray;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.common.Button;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IHasValue;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.ISetValue;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IButton;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex.IForm;

import java.lang.reflect.Field;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.exception;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.LinqUtils.foreach;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.PrintUtils.*;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.ReflectionUtils.getFieldValue;
import static com.ggasoftware.jdi_ui_tests.core.utils.common.ReflectionUtils.getFields;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations.AnnotationsUtil.getElementName;
import static com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations.GetElement.namesEqual;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class Form<T> extends Element implements IForm<T> {
    private boolean fillEmptyValue = false;
    public Form fillEmptyValue() { fillEmptyValue = true; return this; }

    protected void setValueAction(String text, ISetValue element) {
        element.setValue(text);
    }
    protected String getValueAction(IHasValue element) {
        return element.getValue();
    }
    public void fill(MapArray<String, String> objStrings) {
        foreach(getFields(this, ISetValue.class), element -> {
            String fieldValue = objStrings.first((name, value) ->
                    namesEqual(name, getElementName(element)));
            if (fieldValue != null) {
                ISetValue setValueElement = (ISetValue) getFieldValue(element, this);
                doActionRule.invoke(fieldValue, val -> setValueAction(val, setValueElement));
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
                throw exception("Can't find any buttons on form '%s.", toString());
            case 1:
                return (Button) getFieldValue(fields.get(0), this);
            default:
                throw exception("Form '%s' have more than 1 button. Use submit(entity, buttonName) for this case instead", toString());
        }
    }
    public void submit(MapArray<String, String> objStrings) {
        fill(objStrings);
        getSubmitButton().click();
    }
    private void setText(String text) {
        Field field = getFields(this, ISetValue.class).get(0);
        ISetValue setValueElement = (ISetValue) getFieldValue(field, this);
        doActionRule.invoke(text, val -> setValueAction(val, setValueElement));
    }
    public void submit(String text) {
        setText(text);
        getSubmitButton().click();
    }
    public void submit(T entity) { submit(objToSetValue(entity)); }
    public void submit(T entity, String buttonName) {
        fill(objToSetValue(entity));
        getElement.getButton(buttonName).click();
    }
    public void submit(String text, String buttonName) {
        setText(text);
        getElement.getButton(buttonName).click();
    }
    public void submit(T entity, Enum buttonName) {
        fill(objToSetValue(entity));
        getElement.getButton(buttonName.toString().toLowerCase()).click();
    }
    public void verify(MapArray<String, String> objStrings, JActionTT<String, String> compare) {
        foreach(getFields(this, IHasValue.class), element -> {
            String fieldValue = objStrings.first((name, value) ->
                    namesEqual(name, getElementName(element)));
            if (fieldValue != null) {
                IHasValue getValueElement = (IHasValue) getFieldValue(element, this);
                doActionRule.invoke(fieldValue, val -> compare.invoke(getValueAction(getValueElement).trim(), val));
            }
        });
    }
    public void verify(T entity) {
        verify(objToSetValue(entity), asserter::areEquals);
    }

    protected void setValueAction(String value) { submit(parseObjectAsString(value)); }
    protected String getValueAction() { return print(LinqUtils.select(getFields(this, IHasValue.class), field ->
            ((IHasValue) getFieldValue(field, this)).getValue())); }

    public final String getValue() { return actions.getValue(this::getValueAction); }
    public final void setValue(String value) { actions.setValue(value, this::setValueAction); }

}
