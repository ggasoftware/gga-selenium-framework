/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty ofMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */
 
package com.ggasoftware.jdiuitest.web.selenium.elements.composite;

import com.ggasoftware.jdiuitest.core.annotations.AnnotationsUtil;
import com.ggasoftware.jdiuitest.core.interfaces.base.IHasValue;
import com.ggasoftware.jdiuitest.core.interfaces.base.ISetValue;
import com.ggasoftware.jdiuitest.core.interfaces.common.IButton;
import com.ggasoftware.jdiuitest.core.interfaces.complex.IForm;
import com.ggasoftware.jdiuitest.core.utils.common.LinqUtils;
import com.ggasoftware.jdiuitest.core.utils.common.PrintUtils;
import com.ggasoftware.jdiuitest.core.utils.map.MapArray;
import com.ggasoftware.jdiuitest.web.selenium.elements.base.Element;
import com.ggasoftware.jdiuitest.web.selenium.elements.common.Button;
import com.ggasoftware.jdiuitest.web.selenium.elements.pageobjects.annotations.GetElement;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.ggasoftware.jdiuitest.core.settings.JDISettings.exception;
import static com.ggasoftware.jdiuitest.core.utils.common.LinqUtils.foreach;
import static com.ggasoftware.jdiuitest.core.utils.common.PrintUtils.objToSetValue;
import static com.ggasoftware.jdiuitest.core.utils.common.PrintUtils.print;
import static com.ggasoftware.jdiuitest.core.utils.common.ReflectionUtils.getFields;
import static com.ggasoftware.jdiuitest.core.utils.common.ReflectionUtils.getValueField;
import static com.ggasoftware.jdiuitest.core.utils.common.StringUtils.LINE_BREAK;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class Form<T> extends Element implements IForm<T> {
    private boolean fillEmptyValue = false;

    public Form fillEmptyValue() {
        fillEmptyValue = true;
        return this;
    }

    protected void setValueAction(String text, ISetValue element) {
        element.setValue(text);
    }

    protected String getValueAction(IHasValue element) {
        return element.getValue();
    }

    public void fill(MapArray<String, String> objStrings) {
        foreach(getFields(this, ISetValue.class), element -> {
            String fieldValue = objStrings.first((name, value) ->
                    GetElement.namesEqual(name, AnnotationsUtil.getElementName(element)));
            if (fieldValue != null) {
                ISetValue setValueElement = (ISetValue) getValueField(element, this);
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
                return (Button) getValueField(fields.get(0), this);
            default:
                throw exception("Form '%s' have more than 1 button. Use submit(entity, buttonName) for this case instead", toString());
        }
    }

    public void submit(MapArray<String, String> objStrings) {
        fill(objStrings);
        getElement.getButton("submit").click();
    }

    private void setText(String text) {
        Field field = getFields(this, ISetValue.class).get(0);
        ISetValue setValueElement = (ISetValue) getValueField(field, this);
        doActionRule.invoke(text, val -> setValueAction(val, setValueElement));
    }

    public void submit(String text) {
        setText(text);
        getElement.getButton("submit").click();
    }

    public void submit(T entity) {
        submit(objToSetValue(entity));
    }

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

    public List<String> verify(MapArray<String, String> objStrings) {
        List<String> compareFalse = new ArrayList<>();
        foreach(getFields(this, IHasValue.class), field -> {
            String fieldValue = objStrings.first((name, value) ->
                    GetElement.namesEqual(name, AnnotationsUtil.getElementName(field)));
            if (fieldValue != null) {
                IHasValue valueField = (IHasValue) getValueField(field, this);
                doActionRule.invoke(fieldValue, expected -> {
                    String actual = getValueAction(valueField).trim();
                    if (!actual.equals(expected))
                        compareFalse.add(format("Field '%s' (Actual: '%s' <> Expected: '%s')", field.getName(), actual, expected));
                });
            }
        });
        return compareFalse;
    }

    public List<String> verify(T entity) {
        return verify(objToSetValue(entity));
    }

    public void check(T entity) {
        List<String> result = verify(entity);
        if (result.size() > 0)
            throw exception("Check form failed:" + LINE_BREAK + print(result, LINE_BREAK));
    }

    protected String getValueAction() {
        return PrintUtils.print(LinqUtils.select(getFields(this, IHasValue.class), field ->
                ((IHasValue) getValueField(field, this)).getValue()));
    }

    protected void setValueAction(String value) {
        submit(PrintUtils.parseObjectAsString(value));
    }

    public final String getValue() {
        return actions.getValue(this::getValueAction);
    }

    public final void setValue(String value) {
        actions.setValue(value, this::setValueAction);
    }

}
