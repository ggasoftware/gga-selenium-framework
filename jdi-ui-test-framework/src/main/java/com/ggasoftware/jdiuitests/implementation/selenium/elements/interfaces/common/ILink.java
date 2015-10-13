package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IClickable;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface ILink extends IClickable, IText {
    /** Get link destination */
    String getReference();
    /** Wait while link destination contains expected text. Returns link destination */
    String waitReference(String text);
    /** Wait while link destination contains expected text. Returns link destination */
    String waitMatchReference(String regEx);
    /** Get links tooltip */
    String getTooltip();
}
