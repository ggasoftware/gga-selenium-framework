package com.epam.ui_test_framework.elements.interfaces.complex;

import com.epam.ui_test_framework.elements.interfaces.base.IComposite;
import com.epam.ui_test_framework.elements.interfaces.base.IHaveValue;
import com.epam.ui_test_framework.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public interface IPopup extends IHaveValue, IComposite {
    @JDIAction
    void ok();
    @JDIAction
    void cancel();
    @JDIAction
    void close();
}
