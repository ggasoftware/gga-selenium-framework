package com.ggasoftware.uitest.control.interfaces.complex;

import com.ggasoftware.uitest.control.base.annotations.JDIAction;
import com.ggasoftware.uitest.control.interfaces.base.IComposite;
import com.ggasoftware.uitest.control.interfaces.base.IElement;
import com.ggasoftware.uitest.control.interfaces.base.ISetValue;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public interface IForm<T, P> extends IComposite, ISetValue, IElement<P> {
    @JDIAction
    void fill(T entity);

    @JDIAction
    void submit(T entity);
}
