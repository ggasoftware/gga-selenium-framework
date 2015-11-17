package com.ggasoftware.jdiuitest.core.interfaces.complex;

import com.ggasoftware.jdiuitest.core.annotations.JDIAction;
import com.ggasoftware.jdiuitest.core.interfaces.base.IClickable;
import com.ggasoftware.jdiuitest.core.interfaces.common.IText;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IDropDown<TEnum extends Enum> extends ISelector<TEnum>, IText, IClickable {
    /** Expanding DropDown */
    @JDIAction
    void expand();

    /** Closing DropDown */
    @JDIAction
    void close();
}
