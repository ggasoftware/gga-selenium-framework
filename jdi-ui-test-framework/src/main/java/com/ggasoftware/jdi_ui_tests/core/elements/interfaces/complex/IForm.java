package com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.IBaseElement;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.ISetValue;
import com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.JDIAction;
import com.ggasoftware.jdi_ui_tests.utils.map.MapArray;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public interface IForm<T> extends ISetValue, IBaseElement {
    /** Fills all elements on the form which implements SetValue interface and can be matched with fields in input entity */
    @JDIAction
    void fill(T entity);
    @JDIAction
    void fill(MapArray<String, String> objStrings);
    /** Fill all SetValue elements and click on Button “submit” <br>
     *  To use this option AForm pageObject should have only one IButton element */
    @JDIAction
    void submit(T entity);
    @JDIAction
    void submit(MapArray<String, String> objStrings);
    /** Fill all SetValue elements and click on Button specified button e.g. "Publish" or "Save" <br>
     *  To use this option AForm pageObject should have button names in specific format <br>
     *  e.g. if you call submit(user, "Publish") then you should have element 'publishButton'. <br>
     *  * Letters case in button name  no matters
     *  */
    @JDIAction
    void submit(T entity, String buttonName);
    @JDIAction
    void submit(T entity, Enum buttonName);
}
