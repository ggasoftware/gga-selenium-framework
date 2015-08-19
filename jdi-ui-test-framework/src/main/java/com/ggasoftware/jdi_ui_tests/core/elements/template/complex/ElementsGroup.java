package com.ggasoftware.jdi_ui_tests.core.elements.template.complex;

import com.ggasoftware.jdi_ui_tests.core.elements.template.base.BaseElement;
import com.ggasoftware.jdi_ui_tests.core.elements.template.base.Element;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.IGroup;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.utils.common.EnumUtils.getEnumValue;
import static com.ggasoftware.jdi_ui_tests.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.String.format;


/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public abstract class ElementsGroup<TEnum extends Enum, TType extends Element> extends BaseElement implements IGroup<TEnum, TType> {

    protected abstract TType createInstance();

    public TType get(TEnum name) { return get(getEnumValue(name)); }
    public TType get(String name) {
        TType instance = tryGetResult(() -> createInstance());
        if (instance == null)
            throw asserter.exception(format("Can't get instance of '%s' element from Elements Group '%s'", name, toString()));
        instance.copyFromTemplate(instance, name);
        return instance;
    }
}
