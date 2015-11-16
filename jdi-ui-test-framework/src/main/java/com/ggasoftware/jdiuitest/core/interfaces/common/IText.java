package com.ggasoftware.jdiuitest.core.interfaces.common;

import com.ggasoftware.jdiuitest.core.interfaces.base.IElement;
import com.ggasoftware.jdiuitest.core.interfaces.base.IHasValue;
import com.ggasoftware.jdiuitest.core.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IText extends IHasValue, IElement {
    /**
     * @return Get Element’s text
     */
    @JDIAction
    String getText();

    /**
     * @param text Specify expected text
     * @return Wait while Element’s text contains expected text. Returns Element’s text
     */
    @JDIAction
    String waitText(String text);

    /**
     * @param regEx Specify expected regular expression Text
     * @return Wait while Element’s text matches regEx. Returns Element’s text
     */
    @JDIAction
    String waitMatchText(String regEx);
}
