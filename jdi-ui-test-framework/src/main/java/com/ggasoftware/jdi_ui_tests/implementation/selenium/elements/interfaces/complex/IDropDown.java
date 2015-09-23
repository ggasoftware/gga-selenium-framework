package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IClickable;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.common.IText;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IDropDown<TEnum extends Enum> extends ISelector<TEnum>, IText, IClickable {
    void expand();
    void close();
}
