package com.ggasoftware.jdiuitests.implementation.selenium.elements.complex;

import com.ggasoftware.jdiuitests.core.settings.JDISettings;
import com.ggasoftware.jdiuitests.core.utils.common.WebDriverByUtils;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.base.Element;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.IGroup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.ggasoftware.jdiuitests.core.utils.common.EnumUtils.getEnumValue;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.CascadeInit.InitElements;


/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class ElementsGroup<TEnum extends Enum, TType extends Element> extends BaseElement implements IGroup<TEnum, TType> {
    private Class<TType> clazz;

    public ElementsGroup() {
    }

    public ElementsGroup(Class<TType> clazz) {
        this.clazz = clazz;
    }

    public ElementsGroup(By byLocator, Class<TType> clazz) {
        super(byLocator);
        this.clazz = clazz;
    }

    public TType get(TEnum name) {
        return get(getEnumValue(name));
    }

    public TType get(int index, Class<TType> clazz) {
        this.clazz = clazz;
        return get(index);
    }
    public TType get(int index) {
        TType instance;
        try {
            instance = clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException ex) {
            throw JDISettings.exception("Can't get instance of '%s' Element from Elements Group '%s'", index, toString());
        }
        List<WebElement> elements = avatar.getElements();
        InitElements(instance);

        return instance;
    }
    public TType get(String name, Class<TType> clazz) {
        this.clazz = clazz;
        return get(name);
    }
    public TType get(String name) {
        TType instance;
        try {
            instance = clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException ex) {
            throw JDISettings.exception("Can't get instance of '%s' Element from Elements Group '%s'", name, toString());
        }
        instance.setAvatar(WebDriverByUtils.fillByTemplate(getLocator(), name), getAvatar());
        InitElements(instance);
        return instance;
    }
}
