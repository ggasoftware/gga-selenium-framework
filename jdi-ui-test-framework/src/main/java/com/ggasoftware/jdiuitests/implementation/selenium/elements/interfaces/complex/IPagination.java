package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IComposite;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/29/2015.
 */
public interface IPagination extends IBaseElement, IComposite {
    /**
     * @return Choose Next page
     */
    @JDIAction
    void next();

    /**
     * @return Choose Previous page
     */
    @JDIAction
    void previous();

    /**
     * @return Choose First page
     */
    @JDIAction
    void first();

    /**
     * @return Choose Last page
     */
    @JDIAction
    void last();

    /**
     * @param index Specify page index
     * @return Choose page by index
     */
    @JDIAction
    void selectPage(int index);
}
