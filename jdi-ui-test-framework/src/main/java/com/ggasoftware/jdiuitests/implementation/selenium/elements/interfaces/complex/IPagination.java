package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IComposite;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations.JDIAction;

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
    /** Choose First page */
    @JDIAction
    void first();
    /** Choose Last page */
    @JDIAction
    void last();
    /** Choose page by index */
    @JDIAction
    void selectPage(int index);
}
