package com.ggasoftware.jdiuitest.core.interfaces.complex;

import com.ggasoftware.jdiuitest.core.interfaces.base.IMultiSelector;
import com.ggasoftware.jdiuitest.core.interfaces.common.IText;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IDropList<TEnum extends Enum> extends IMultiSelector<TEnum>, IText {
}
