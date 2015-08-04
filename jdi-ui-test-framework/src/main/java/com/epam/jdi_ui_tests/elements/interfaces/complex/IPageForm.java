package com.epam.jdi_ui_tests.elements.interfaces.complex;

import com.epam.jdi_ui_tests.elements.interfaces.base.IBaseElement;
import com.epam.jdi_ui_tests.elements.interfaces.base.IComposite;
import com.epam.jdi_ui_tests.elements.interfaces.base.IElement;
import com.epam.jdi_ui_tests.elements.interfaces.base.ISetValue;
import com.epam.jdi_ui_tests.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public interface IPageForm<T> extends IComposite, IBaseElement {
    /** Fills all elements on the form which implements SetValue interface and can be matched with fields in input entity */
    @JDIAction
    void fill(T entity);
    /** Fill all SetValue elements and click on Button “submit” <br>
     *  To use this option Form pageObject should have only one IButton element */
    @JDIAction
    void submit(T entity);
    /** Fill all SetValue elements and click on Button specified button e.g. "Publish" or "Save" <br>
     *  To use this option Form pageObject should have button names in specific format <br>
     *  e.g. if you call "submit(user, "Publish") then you should have element 'publishButton'. <br>
     *  * Letters case in button name  no matters
     *  */
    @JDIAction
    void submit(T entity, String buttonName);
}
