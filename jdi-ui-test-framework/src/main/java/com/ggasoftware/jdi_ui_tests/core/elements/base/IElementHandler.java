package com.ggasoftware.jdi_ui_tests.core.elements.base;

import com.ggasoftware.jdi_ui_tests.utils.linqInterfaces.JFuncTT;

import java.util.List;

/**
 * Created by Roman_Iovlev on 8/17/2015.
 */
public interface IElementHandler<TDriver, TElement> {
    TDriver getDriver();
    TElement getElement();
    List<TElement> getElements();
    boolean hasLocator();
    void setLocalElementSearchCriteria(JFuncTT<TElement, Boolean> criteria);
}
