package com.ggasoftware.jdiuitest.core.interfaces.common;

import com.ggasoftware.jdiuitest.core.interfaces.base.IClickable;
import com.ggasoftware.jdiuitest.core.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public interface IImage extends IClickable {
    /**
     * @return Get image source
     */
    @JDIAction
    String getSource();

    /**
     * @return Get image alt/hint text
     */
    @JDIAction
    String getAlt();
}
