package com.ggasoftware.uitest.control.interfaces.common;

import com.ggasoftware.uitest.control.base.annotations.JDIAction;
import com.ggasoftware.uitest.control.interfaces.base.IElement;
import com.ggasoftware.uitest.control.interfaces.base.IHaveValue;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IText<P> extends IHaveValue, IElement<P> {
    @JDIAction
    String getText();

    @JDIAction
    String waitText(String text);

    @JDIAction
    String waitMatchText(String regEx);
}
