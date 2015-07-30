package com.epam.ui_test_framework.elements.interfaces.complex;

import com.epam.ui_test_framework.elements.interfaces.base.IBaseElement;
import com.epam.ui_test_framework.elements.interfaces.base.IComposite;
import com.epam.ui_test_framework.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/29/2015.
 */
public interface IPagination extends IBaseElement, IComposite {
    @JDIAction
    void next();
    @JDIAction
    void previous();
    @JDIAction
    void first();
    @JDIAction
    void last();
    @JDIAction
    void selectPage(int index);
}
