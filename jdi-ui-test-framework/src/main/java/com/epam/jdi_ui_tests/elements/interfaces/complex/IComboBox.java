package com.epam.jdi_ui_tests.elements.interfaces.complex;

import com.epam.jdi_ui_tests.elements.interfaces.common.ITextField;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IComboBox<TEnum extends Enum> extends IDropDown<TEnum>, ITextField {

}
