package com.ggasoftware.uitest.control.interfaces.complex;

import com.ggasoftware.uitest.control.Element;
import com.ggasoftware.uitest.control.interfaces.base.IBaseElement;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public interface IGroup<TEnum extends Enum, TType extends Element> extends IBaseElement {
    TType get(TEnum name);
    TType get(String name);
}
