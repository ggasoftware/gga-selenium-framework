package com.ggasoftware.jdiuitests.implementation.selenium.elements.composite;

import com.ggasoftware.jdiuitests.core.utils.common.LinqUtils;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JActionTT;
import com.ggasoftware.jdiuitests.core.utils.map.MapArray;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.common.Button;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IHasValue;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.ISetValue;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common.IButton;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.IForm;
import com.ggasoftware.jdiuitests.core.settings.JDISettings;
import com.ggasoftware.jdiuitests.core.utils.common.PrintUtils;
import com.ggasoftware.jdiuitests.core.utils.common.ReflectionUtils;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations.AnnotationsUtil;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations.GetElement;

import java.lang.reflect.Field;
import java.util.List;

import static com.ggasoftware.jdiuitests.core.utils.common.LinqUtils.foreach;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations.AnnotationsUtil.getElementName;

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
        LinqUtils.foreach(ReflectionUtils.getFields(this, ISetValue.class), element -> {
            String fieldValue = objStrings.first((name, value) ->
                    GetElement.namesEqual(name, AnnotationsUtil.getElementName(element)));
            if (fieldValue != null) {
                ISetValue setValueElement = (ISetValue) ReflectionUtils.getFieldValue(element, this);
                doActionRule.invoke(fieldValue, val -> setValueAction(val, setValueElement));
            }
        });
    }
    public void fill(T entity) {
        fill(PrintUtils.objToSetValue(entity));
    }

    private Button getSubmitButton() {
        List<Field> fields = ReflectionUtils.getFields(this, IButton.class);
        switch (fields.size()) {
            case 0:
                throw JDISettings.exception("Can't find any buttons on form '%s.", toString());
            case 1:
                return (Button) ReflectionUtils.getFieldValue(fields.get(0), this);
            default:
                throw JDISettings.exception("Form '%s' have more than 1 button. Use submit(entity, buttonName) for this case instead", toString());
        }
    }
    public void submit(MapArray<String, String> objStrings) {
        fill(objStrings);
        getSubmitButton().click();
    }
    private void setText(String text) {
        Field field = ReflectionUtils.getFields(this, ISetValue.class).get(0);
        ISetValue setValueElement = (ISetValue) ReflectionUtils.getFieldValue(field, this);
        doActionRule.invoke(text, val -> setValueAction(val, setValueElement));
    }
    public void submit(String text) {
        setText(text);
        getSubmitButton().click();
    }
    public void submit(T entity) { submit(PrintUtils.objToSetValue(entity)); }
    public void submit(T entity, String buttonName) {
        fill(PrintUtils.objToSetValue(entity));
        getElement.getButton(buttonName).click();
    }
    public void submit(String text, String buttonName) {
        setText(text);
        getElement.getButton(buttonName).click();
    }
    public void submit(T entity, Enum buttonName) {
        fill(PrintUtils.objToSetValue(entity));
        getElement.getButton(buttonName.toString().toLowerCase()).click();
    }
    public void verify(MapArray<String, String> objStrings, JActionTT<String, String> compare) {
        LinqUtils.foreach(ReflectionUtils.getFields(this, IHasValue.class), element -> {
            String fieldValue = objStrings.first((name, value) ->
                    GetElement.namesEqual(name, AnnotationsUtil.getElementName(element)));
            if (fieldValue != null) {
                IHasValue getValueElement = (IHasValue) ReflectionUtils.getFieldValue(element, this);
                doActionRule.invoke(fieldValue, val -> compare.invoke(getValueAction(getValueElement).trim(), val));
            }
        });
    }
    public void verify(T entity) {
        verify(PrintUtils.objToSetValue(entity), JDISettings.asserter::areEquals);
    }

    protected void setValueAction(String value) { submit(PrintUtils.parseObjectAsString(value)); }
    protected String getValueAction() { return PrintUtils.print(LinqUtils.select(ReflectionUtils.getFields(this, IHasValue.class), field ->
            ((IHasValue) ReflectionUtils.getFieldValue(field, this)).getValue())); }

    public final String getValue() { return actions.getValue(this::getValueAction); }
    public final void setValue(String value) { actions.setValue(value, this::setValueAction); }

}
