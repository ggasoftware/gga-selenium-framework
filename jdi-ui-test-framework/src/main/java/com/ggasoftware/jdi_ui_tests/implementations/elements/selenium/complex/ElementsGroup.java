package com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.complex;

import com.ggasoftware.jdi_ui_tests.core.elements.base.ABase;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.IGroup;
import com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.base.WebBaseElement;
import com.ggasoftware.jdi_ui_tests.utils.usefulUtils.TryCatchUtil;
import org.openqa.selenium.By;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.utils.common.EnumUtils.getEnumValue;
import static java.lang.String.format;


/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class ElementsGroup<TEnum extends Enum, TType extends WebBaseElement> extends ABase implements IGroup<TEnum, TType> {
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
        TType instance = TryCatchUtil.tryGetResult(clazz::newInstance);
        if (instance == null)
            throw asserter.exception(format("Can't get instace of '%s' element from Elements Group '%s'", name, toString()));
        instance.locationInfo.init(fillByTemplateSilent(getLocator(), name), getLocationInfo(), instance);
        return instance;
    }
}
