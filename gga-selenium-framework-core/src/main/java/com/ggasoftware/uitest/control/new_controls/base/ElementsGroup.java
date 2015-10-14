package com.ggasoftware.uitest.control.new_controls.base;

import com.ggasoftware.uitest.control.Element;
import com.ggasoftware.uitest.control.interfaces.complex.IGroup;
import org.openqa.selenium.By;

import static com.ggasoftware.uitest.control.base.asserter.testNG.Assert.exception;
import static com.ggasoftware.uitest.utils.EnumUtils.getEnumValue;
import static java.lang.String.format;


/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class ElementsGroup<TEnum extends Enum, TType extends Element> extends BaseElement implements IGroup<TEnum, TType> {
    private Class<TType> clazz;

    public ElementsGroup() {
    }

    public ElementsGroup(By byLocator, Class<TType> clazz) {
        super(byLocator);
        this.clazz = clazz;
    }

    public TType get(TEnum name) {
        return get(getEnumValue(name));
    }

    public TType get(String name) {
        TType instance;
        try {
            instance = clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException ex) {
            throw exception(format("Can't get instance of '%s' Element from Elements Group '%s'", name, toString()));
        }
        return instance;
    }
}
