package com.epam.jdi_ui_tests.elements.interfaces.complex;

import com.epam.jdi_ui_tests.elements.interfaces.base.ISelector;
import com.epam.jdi_ui_tests.elements.interfaces.common.IText;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IDropDown<TEnum extends Enum> extends ISelector<TEnum>, IText {

}
