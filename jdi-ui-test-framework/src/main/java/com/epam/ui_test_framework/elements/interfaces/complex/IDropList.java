package com.epam.ui_test_framework.elements.interfaces.complex;

import com.epam.ui_test_framework.elements.interfaces.base.IMultiSelector;
import com.epam.ui_test_framework.elements.interfaces.common.IText;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IDropList<TEnum extends Enum> extends IMultiSelector<TEnum>, IText {
}
