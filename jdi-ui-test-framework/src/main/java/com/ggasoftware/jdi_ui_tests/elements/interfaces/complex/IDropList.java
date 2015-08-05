package com.ggasoftware.jdi_ui_tests.elements.interfaces.complex;

import com.ggasoftware.jdi_ui_tests.elements.interfaces.base.IMultiSelector;
import com.ggasoftware.jdi_ui_tests.elements.interfaces.common.IText;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IDropList<TEnum extends Enum> extends IMultiSelector<TEnum>, IText {
}
