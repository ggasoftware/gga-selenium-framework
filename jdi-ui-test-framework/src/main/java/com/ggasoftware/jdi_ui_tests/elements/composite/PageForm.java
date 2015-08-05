package com.ggasoftware.jdi_ui_tests.elements.composite;

import com.ggasoftware.jdi_ui_tests.elements.common.Button;
import com.ggasoftware.jdi_ui_tests.elements.interfaces.base.ISetValue;
import com.ggasoftware.jdi_ui_tests.elements.interfaces.common.IButton;
import com.ggasoftware.jdi_ui_tests.elements.interfaces.complex.IPageForm;
import com.ggasoftware.jdi_ui_tests.elements.BaseElement;
import com.ggasoftware.jdi_ui_tests.settings.JDISettings;
import com.ggasoftware.jdi_ui_tests.utils.common.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

import static com.ggasoftware.jdi_ui_tests.elements.page_objects.annotations.AnnotationsUtil.getElementName;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.first;
import static com.ggasoftware.jdi_ui_tests.utils.common.LinqUtils.foreach;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/17/2015.
 */
public class PageForm<T> extends Page implements IPageForm<T> {
    public PageForm() {}
    public PageForm(String url) {
        super(url);
    }
    public PageForm(String url, String title) { super(url, title); }

    protected void setValueAction(String text, ISetValue element) {
        element.setValue(text);
    }

    public void fill(T entity) {
        Field[] fields = entity.getClass().getFields();
        foreach(ReflectionUtils.getFields(this, ISetValue.class), element -> {
            Field field = first(fields, f -> namesEqual(f.getName(), getElementName(element)));
            if (field != null) {
                ISetValue setValueElement = (ISetValue) ReflectionUtils.getFieldValue(element, this);
                BaseElement.setValueRule.invoke(field.get(entity) + "", val -> setValueAction(val, setValueElement));
            }
        });
    }

    public void submit(T entity) {
        fill(entity);
        getSubmitButton().click();
    }

    public void submit(T entity, String buttonName) {
        fill(entity);
        getButton(buttonName).click();
    }

    private boolean namesEqual(String name1, String name2) {
        return name1.toLowerCase().replace(" ", "").equals(name2.toLowerCase().replace(" ", ""));
    }

    private Button getSubmitButton() {
        List<Field> fields = ReflectionUtils.getFields(this, IButton.class);
        switch (fields.size()) {
            case 0:
                JDISettings.asserter.exception(format("Can't find any buttons on form '%s.", toString()));
                return null;
            case 1:
                return (Button) ReflectionUtils.getFieldValue(fields.get(0), this);
            default:
                JDISettings.asserter.exception(format("Form '%s' have more than 1 button. Use submit(entity, buttonName) for this case instead", toString()));
                return null;
        }
    }
}
