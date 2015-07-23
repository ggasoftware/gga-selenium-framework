package com.ggasoftware.uitest.control.interfaces.complex;

import com.ggasoftware.uitest.control.interfaces.base.ISelector;
import com.ggasoftware.uitest.control.interfaces.common.IText;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IDropDown<TEnum extends Enum, P> extends ISelector<TEnum>, IText<P> {

}
