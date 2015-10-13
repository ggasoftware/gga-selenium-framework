package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.complex;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex.IGroup;
import com.ggasoftware.jdi_ui_tests.core.settings.JDISettings;
import com.ggasoftware.jdi_ui_tests.core.utils.common.WebDriverByUtils;
import org.openqa.selenium.By;

import static com.ggasoftware.jdi_ui_tests.core.utils.common.EnumUtils.getEnumValue;


/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class ElementsGroup<TEnum extends Enum, TType extends Element> extends BaseElement implements IGroup<TEnum, TType> {
    public ElementsGroup() { }
    public ElementsGroup(Class<TType> clazz) {
        this.clazz = clazz;
    }
    public ElementsGroup(By byLocator, Class<TType> clazz) {
        super(byLocator);
        this.clazz = clazz;
    }
    private Class<TType> clazz;

    public TType get(TEnum name) {
        return get(getEnumValue(name));
    }
    public TType get(String name) {
        TType instance;
        try { instance = clazz.newInstance();
        } catch (IllegalAccessException|InstantiationException ex) {
            throw JDISettings.exception("Can't get instance of '%s' Element from Elements Group '%s'", name, toString());
        }
        instance.setAvatar(WebDriverByUtils.fillByTemplate(getLocator(), name), getAvatar());
        return instance;
    }
}
