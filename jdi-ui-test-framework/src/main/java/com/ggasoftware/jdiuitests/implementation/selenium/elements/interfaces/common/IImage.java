package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IClickable;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/9/2015.
 */
public interface IImage extends IClickable {
    /** Get image source */
    @JDIAction
    String getSource();
    /** Get image alt/hint text */
    @JDIAction
    String getAlt();
}
