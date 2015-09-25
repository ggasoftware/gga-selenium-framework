package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IComposite;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.base.ISetValue;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public interface IForm<T> extends IComposite, ISetValue, IElement {
    /** Fills all elements on the form which implements SetValue interface and can be matched with fields in input entity */
    @JDIAction
    void fill(T entity);
    /** Fill first setable field with valueand click on Button “submit” <br>
     *  To use this option Form pageObject should have only one IButton Element */
    @JDIAction
    void submit(String text);
    @JDIAction
    default void add(String text) {submit(text);}
    /** Fill all SetValue elements and click on Button “submit” <br>
     *  To use this option Form pageObject should have only one IButton Element */
    @JDIAction
    void submit(T entity);
    @JDIAction
    default void login(T entity) { submit(entity); }
    @JDIAction
    default void add(T entity) { submit(entity); }
    @JDIAction
    default void publish(T entity) { submit(entity); }
    @JDIAction
    default void save(T entity) { submit(entity); }
    /** Fill all SetValue elements and click on Button specified button e.g. "Publish" or "Save" <br>
     *  To use this option Form pageObject should have button names in specific format <br>
     *  e.g. if you call "submit(user, "Publish") then you should have Element 'publishButton'. <br>
     *  * Letters case in button name  no matters
     *  */
    @JDIAction
    void submit(T entity, String buttonName);

    /** Fill all SetValue elements and click on Button specified button e.g. "Publish" or "Save" <br>
     *  To use this option Form pageObject should have button names in specific format <br>
     *  e.g. if you call "submit(user, "Publish") then you should have Element 'publishButton'. <br>
     *  * Letters case in button name  no matters
     *  */
    @JDIAction
    void submit(T entity, Enum buttonName);
}
