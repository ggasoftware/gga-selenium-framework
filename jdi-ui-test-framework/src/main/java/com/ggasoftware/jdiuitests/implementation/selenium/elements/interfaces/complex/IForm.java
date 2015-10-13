package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IComposite;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.ISetValue;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations.JDIAction;

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
    void submit(String text, String buttonName);
    @JDIAction
    default void login(String text) { submit(text, "login"); }
    @JDIAction
    default void add(String text) { submit(text, "add"); }
    @JDIAction
    default void publish(String text) { submit(text, "publish"); }
    @JDIAction
    default void save(String text) { submit(text, "save"); }
    @JDIAction
    default void update(String text) { submit(text, "update"); }
    @JDIAction
    default void cancel(String text) { submit(text, "cancel"); }
    @JDIAction
    default void close(String text) { submit(text, "close"); }
    @JDIAction
    default void back(String text) { submit(text, "back"); }
    @JDIAction
    default void select(String text) { submit(text, "select"); }
    @JDIAction
    default void next(String text) { submit(text, "next"); }
    /** Fill all SetValue elements and click on Button “submit” <br>
     *  To use this option Form pageObject should have only one IButton Element */
    @JDIAction
    void submit(T entity);
    @JDIAction
    default void login(T entity) { submit(entity, "login"); }
    @JDIAction
    default void add(T entity) { submit(entity, "add"); }
    @JDIAction
    default void publish(T entity) { submit(entity, "publish"); }
    @JDIAction
    default void save(T entity) { submit(entity, "save"); }
    @JDIAction
    default void update(T entity) { submit(entity, "update"); }
    @JDIAction
    default void cancel(T entity) { submit(entity, "cancel"); }
    @JDIAction
    default void close(T entity) { submit(entity, "close"); }
    @JDIAction
    default void back(T entity) { submit(entity, "back"); }
    @JDIAction
    default void select(T entity) { submit(entity, "select"); }
    @JDIAction
    default void next(T entity) { submit(entity, "next"); }
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
