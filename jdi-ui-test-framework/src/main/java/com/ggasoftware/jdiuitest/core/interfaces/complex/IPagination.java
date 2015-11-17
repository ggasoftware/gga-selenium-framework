package com.ggasoftware.jdiuitest.core.interfaces.complex;

import com.ggasoftware.jdiuitest.core.annotations.JDIAction;
import com.ggasoftware.jdiuitest.core.interfaces.base.IBaseElement;
import com.ggasoftware.jdiuitest.core.interfaces.base.IComposite;

/**
 * Created by Roman_Iovlev on 7/29/2015.
 */
public interface IPagination extends IBaseElement, IComposite {
    /** Choose Next page */
    @JDIAction
    void next();

    /** Choose Previous page */
    @JDIAction
    void previous();

    /** hoose First page */
    @JDIAction
    void first();

    /** Choose Last page */
    @JDIAction
    void last();

    /**
     * @param index Specify page index
     * Choose page by index
     */
    @JDIAction
    void selectPage(int index);
}
