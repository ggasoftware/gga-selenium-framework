package com.ggasoftware.jdiuitest.core.interfaces.base;

import com.ggasoftware.jdiuitest.core.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IElement extends IBaseElement, IVisible {

    /**
     * Get element attribute
     *
     * @param name Specify name for attribute
     * @return Returns chosen attribute
     */
    @JDIAction
    String getAttribute(String name);

    /**
     * @param name  Specify attribute name
     * @param value Specify attribute value
     * @return Waits while attribute gets expected value. Return false if this not happens
     */
    @JDIAction
    boolean waitAttribute(String name, String value);

    /**
     * @param attributeName Specify attribute name
     * @param value         Specify attribute value
     * Sets attribute value for Element
     */
    @JDIAction
    void setAttribute(String attributeName, String value);

}
