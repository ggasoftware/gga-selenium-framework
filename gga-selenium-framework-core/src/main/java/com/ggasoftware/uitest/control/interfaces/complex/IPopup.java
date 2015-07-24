package com.ggasoftware.uitest.control.interfaces.complex;

import com.ggasoftware.uitest.control.base.annotations.JDIAction;
import com.ggasoftware.uitest.control.interfaces.base.IComposite;
import com.ggasoftware.uitest.control.interfaces.base.IHaveValue;

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
