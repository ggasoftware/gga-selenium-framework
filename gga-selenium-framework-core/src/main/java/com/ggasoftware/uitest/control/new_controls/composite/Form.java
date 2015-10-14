package com.ggasoftware.uitest.control.new_controls.composite;

import com.ggasoftware.uitest.control.Element;
import com.ggasoftware.uitest.control.base.map.MapArray;
import com.ggasoftware.uitest.control.interfaces.base.IHaveValue;
import com.ggasoftware.uitest.control.interfaces.base.ISetValue;
import com.ggasoftware.uitest.control.interfaces.complex.IForm;
import org.openqa.selenium.By;

import static com.ggasoftware.uitest.control.base.annotations.AnnotationsUtil.getElementName;
import static com.ggasoftware.uitest.control.base.annotations.functions.Functions.SUBMIT_BUTTON;
import static com.ggasoftware.uitest.utils.LinqUtils.foreach;
import static com.ggasoftware.uitest.utils.LinqUtils.select;
import static com.ggasoftware.uitest.utils.PrintUtils.*;
import static com.ggasoftware.uitest.utils.ReflectionUtils.getFieldValue;
import static com.ggasoftware.uitest.utils.ReflectionUtils.getFields;


/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class Form<T, P> extends Element<P> implements IForm<T, P> {
    private boolean fillEmptyValue = false;

    public Form() {
    }

    public Form(By byLocator) {
        super(byLocator);
    }

    public Form fillEmptyValue() {
        fillEmptyValue = true;
        return this;
    }

    protected void setValueAction(String text, ISetValue element) {
        element.setValue(text);
    }

    protected void fill(MapArray<String, String> objStrings) {
        foreach(getFields(this, ISetValue.class), element -> {
            String fieldValue = objStrings.first(name ->
                    namesEqual(name, getElementName(element)));
            if (fieldValue != null) {
                ISetValue seValueElement = (ISetValue) getFieldValue(element, this);
                setValueRule(fieldValue, val -> setValueAction(val, seValueElement));
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

    protected String getValueAction() {
        return print(select(getFields(this, IHaveValue.class), field ->
                ((IHaveValue) getFieldValue(field, this)).getValue()));
    }

    protected void setValueAction(String value) {
        submit(parseObjectAsString(value));
    }

    public final String getValue() {
        return doJActionResult("Get value", this::getValueAction);
    }

    public final void setValue(String value) {
        doJAction("Set value", () -> setValueRule(value, this::setValueAction));
    }
}
