package com.epam.ui_test_framework.elements.base;

import com.epam.ui_test_framework.elements.BaseElement;
import com.epam.ui_test_framework.elements.interfaces.complex.IGroup;
import org.openqa.selenium.By;

import static com.epam.ui_test_framework.utils.common.EnumUtils.getEnumValue;
import static com.epam.ui_test_framework.utils.common.WebDriverByUtils.fillByTemplateSilent;
import static com.epam.ui_test_framework.utils.usefulUtils.TryCatchUtil.tryGetResult;


/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class ElementsGroup<TEnum extends Enum, TType extends Element> extends BaseElement implements IGroup<TEnum, TType> {
    public ElementsGroup() { }
    public ElementsGroup(By byLocator, Class<TType> clazz) {
        super(byLocator);
        this.clazz = clazz;
    }
    private Class<TType> clazz;

    public TType get(TEnum name) {
        return get(getEnumValue(name));
    }
    public TType get(String name) {
        TType instance = tryGetResult(() -> clazz.newInstance());
        instance.setAvatar(fillByTemplateSilent(getLocator(), name), getAvatar());
        return instance;
    }
}
