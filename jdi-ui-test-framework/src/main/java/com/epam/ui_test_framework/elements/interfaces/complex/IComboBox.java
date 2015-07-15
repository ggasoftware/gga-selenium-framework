package com.epam.ui_test_framework.elements.interfaces.complex;

import com.epam.ui_test_framework.elements.interfaces.simple.IInput;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IComboBox<TEnum extends Enum> extends IDropDown<TEnum>, IInput {

}
