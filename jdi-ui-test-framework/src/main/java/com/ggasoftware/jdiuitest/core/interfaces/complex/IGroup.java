package com.ggasoftware.jdiuitest.core.interfaces.complex;

import com.ggasoftware.jdiuitest.core.interfaces.base.IBaseElement;
import com.ggasoftware.jdiuitest.core.interfaces.base.IElement;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public interface IGroup<TEnum extends Enum, TType extends IElement> extends IBaseElement {

    TType get(TEnum name);

    TType get(String name) throws IllegalAccessException, InstantiationException;
}
