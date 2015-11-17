package com.ggasoftware.jdiuitest.core.interfaces.complex;

import com.ggasoftware.jdiuitest.core.annotations.JDIAction;
import com.ggasoftware.jdiuitest.core.interfaces.base.IBaseElement;
import com.ggasoftware.jdiuitest.core.interfaces.base.IHasValue;
import com.ggasoftware.jdiuitest.core.interfaces.base.IVisible;

import java.util.List;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface ITextList<TEnum extends Enum> extends IBaseElement, IHasValue, IVisible {
    /**
     * @param name Specify string by String mechanic
     * @return Get textList’s text by specified param
     */
    @JDIAction
    String getText(String name);

    /**
     * @param index Specify string by Integer mechanic
     * @return Get textList’s text by specified param
     */
    @JDIAction
    String getText(int index);

    /**
     * @param enumName Specify string by Enum mechanic
     * @return Get textList’s text by specified param
     */
    @JDIAction
    String getText(TEnum enumName);

    /** @return Returns strings count */
    @JDIAction
    int count();

    /** @return Wait while TextList’s text contains expected text. Returns Element’s text */
    @JDIAction
    List<String> waitText(String str);

    /** @return Return list of strings of TextList */
    @JDIAction
    List<String> getTextList();

    /** @return Return first String in list */
    @JDIAction
    String getFirstText();

    /** @return Return last String in list */
    @JDIAction
    String getLastText();
}
