package com.epam.ui_test_framework.elements.composite;

import com.epam.ui_test_framework.elements.base.Element;
import com.epam.ui_test_framework.elements.interfaces.base.IHaveValue;
import com.epam.ui_test_framework.elements.interfaces.complex.IForm;
import com.epam.ui_test_framework.elements.interfaces.base.ISetValue;
import com.epam.ui_test_framework.elements.page_objects.annotations.functions.Functions;
import com.epam.ui_test_framework.utils.common.PrintUtils;
import com.epam.ui_test_framework.utils.map.MapArray;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.util.List;

import static com.epam.ui_test_framework.elements.page_objects.annotations.AnnotationsUtil.getElementName;
import static com.epam.ui_test_framework.elements.page_objects.annotations.functions.Functions.SUBMIT_BUTTON;
import static com.epam.ui_test_framework.utils.common.LinqUtils.first;
import static com.epam.ui_test_framework.utils.common.LinqUtils.foreach;
import static com.epam.ui_test_framework.utils.common.LinqUtils.select;
import static com.epam.ui_test_framework.utils.common.PrintUtils.*;
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

    protected void submit(MapArray<String, String> objStrings) {
        fill(objStrings);
        getButton(SUBMIT_BUTTON).click();
    }
    public void submit(T entity) {
        submit(objToSetValue(entity));
    }
    protected void setValueAction(String value) {
        submit(parseObjectAsString(value));
    }
    protected String getValueAction() {
        return print(select(getFields(this, IHaveValue.class), field ->
                ((IHaveValue) getFieldValue(field, this)).getValue()));
    }
    public final void setValue(String value) { doJAction("Set value", () -> setValueRule(value, this::setValueAction)); }
    public final String getValue() { return doJActionResult("Get value", this::getValueAction); }
}
