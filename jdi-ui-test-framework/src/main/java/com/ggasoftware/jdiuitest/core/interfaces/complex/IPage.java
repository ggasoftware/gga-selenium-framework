package com.ggasoftware.jdiuitest.core.interfaces.complex;

import com.ggasoftware.jdiuitest.core.annotations.JDIAction;
import com.ggasoftware.jdiuitest.core.interfaces.base.IComposite;

/**
 * Created by Roman_Iovlev on 9/3/2015.
 */
public interface IPage extends IComposite {
    /**
     * @return Check that page opened
     */
    @JDIAction
    void checkOpened();

    /**
     * @return Opens url specified for page
     */
    @JDIAction
    void open();
}
