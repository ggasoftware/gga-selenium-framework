package com.epam.ui_test_framework.elements.composite;

import com.epam.ui_test_framework.elements.base.Element;
import com.epam.ui_test_framework.elements.interfaces.complex.IForm;
import com.epam.ui_test_framework.elements.interfaces.base.ISetValue;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.util.List;

import static com.epam.ui_test_framework.elements.page_objects.annotations.AnnotationsUtil.getElementName;
import static com.epam.ui_test_framework.utils.common.LinqUtils.first;
import static com.epam.ui_test_framework.utils.common.LinqUtils.foreach;
import static com.epam.ui_test_framework.utils.common.ReflectionUtils.getFieldValue;
import static com.epam.ui_test_framework.utils.common.ReflectionUtils.getFields;

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

    public void fill(T entity) {
        if (entity == null) return;
        List<Field> values = getFields(entity, Object.class);
        foreach(getFields(this, ISetValue.class), element -> {
            Field fieldWithName = first(values, value ->
                    namesEqual(getElementName(value), getElementName(element)));
            if (fieldWithName != null) {
                Object fieldValue = getFieldValue(fieldWithName, entity);
                String value = (fieldValue == null) ? null : fieldValue.toString();
                ISetValue seValueElement = (ISetValue) getFieldValue(element, this);
                setValueRule.invoke(value, val -> setValueAction(val, seValueElement));
            }
        });
    }

    private boolean namesEqual(String name1, String name2) {
        return name1.toLowerCase().replace(" ", "").equals(name2.toLowerCase().replace(" ", ""));
    }

    public void submit(T entity) {
        fill(entity);
        getButton("submit").click();
    }
}
