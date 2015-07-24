package com.ggasoftware.uitest.control.new_controls.base;

import com.ggasoftware.uitest.control.Element;
import com.ggasoftware.uitest.control.interfaces.complex.IGroup;
import org.openqa.selenium.By;

import static com.ggasoftware.uitest.control.base.usefulUtils.TryCatchUtil.tryGetResult;
import static com.ggasoftware.uitest.utils.EnumUtils.getEnumValue;
import static com.ggasoftware.uitest.utils.WebDriverByUtils.fillByTemplateSilent;


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
