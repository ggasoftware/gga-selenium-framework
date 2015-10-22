package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IClickable;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface ILink extends IClickable, IText {
    /**
     * @return Get link destination
     */
    String getReference();

    /**
     * @param text Specify expected text
     * @return Wait while link destination contains expected text. Returns link destination
     */
    String waitReference(String text);

    /**
     * @param regEx Specify expected regular expression Text
     * @return Wait while link destination contains expected text. Returns link destination
     */
    String waitMatchReference(String regEx);

    /**
     * @return Get links tooltip
     */
    String getTooltip();
}
