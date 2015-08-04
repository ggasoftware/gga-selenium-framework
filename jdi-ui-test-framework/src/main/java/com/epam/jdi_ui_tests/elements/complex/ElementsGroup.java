package com.epam.jdi_ui_tests.elements.complex;

import com.epam.jdi_ui_tests.elements.BaseElement;
import com.epam.jdi_ui_tests.elements.base.Element;
import com.epam.jdi_ui_tests.elements.interfaces.complex.IGroup;
import org.openqa.selenium.By;

import static com.epam.jdi_ui_tests.settings.JDISettings.asserter;
import static com.epam.jdi_ui_tests.utils.common.EnumUtils.getEnumValue;
import static com.epam.jdi_ui_tests.utils.common.WebDriverByUtils.fillByTemplateSilent;
import static com.epam.jdi_ui_tests.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.String.format;


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
        TType instance = tryGetResult(clazz::newInstance);
        if (instance == null) {
            asserter.exception(format("Can't get instace of '%s' element from Elements Group '%s'", name, toString()));
            return null;
        }
        instance.setAvatar(fillByTemplateSilent(getLocator(), name), getAvatar());
        return instance;
    }
}
