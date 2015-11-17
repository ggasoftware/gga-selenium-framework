package com.ggasoftware.jdiuitest.core.interfaces.common;

import com.ggasoftware.jdiuitest.core.annotations.JDIAction;
import com.ggasoftware.jdiuitest.core.interfaces.base.IClickable;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface ILink extends IClickable, IText {
    /** @return Get link destination */
    @JDIAction
    String getReference();

    /**
     * @param text Specify expected text
     * @return Wait while link destination contains expected text. Returns link destination
     */
    @JDIAction
    String waitReference(String text);

    /**
     * @param regEx Specify expected regular expression Text
     * @return Wait while link destination contains expected text. Returns link destination
     */
    @JDIAction
    String waitMatchReference(String regEx);

    /** @return Get links tooltip */
    @JDIAction
    String getTooltip();
}
