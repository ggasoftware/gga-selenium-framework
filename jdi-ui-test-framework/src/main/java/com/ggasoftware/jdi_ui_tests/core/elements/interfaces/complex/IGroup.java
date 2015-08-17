package com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex;

import com.ggasoftware.jdi_ui_tests.core.elements.base.ABaseElement;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IBaseElement;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public interface IGroup<TEnum extends Enum, TType extends ABaseElement> extends IBaseElement {
    /** Get element by name */
    TType get(String name);
    TType get(TEnum name);
}
