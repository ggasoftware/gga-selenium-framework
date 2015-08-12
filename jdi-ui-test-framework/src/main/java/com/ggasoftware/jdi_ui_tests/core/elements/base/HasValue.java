package com.ggasoftware.jdi_ui_tests.core.elements.base;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IHasValue;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public abstract class HasValue<TDriver, TElement, TLocator, TContext> extends Element<TDriver, TElement, TLocator, TContext> implements IHasValue {

    protected abstract String getValueAction();
    public final String getValue() { return doJActionResult("Get value", this::getValueAction); }

}
