package com.ggasoftware.jdiuitest.core.interfaces.complex;

import com.ggasoftware.jdiuitest.core.interfaces.base.IClickable;
import com.ggasoftware.jdiuitest.core.interfaces.common.IText;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IDropDown<TEnum extends Enum> extends ISelector<TEnum>, IText, IClickable {
    /**
     * @return Expanding DropDown
     */
    void expand();

    /**
     * @return Closing DropDown
     */
    void close();
}
