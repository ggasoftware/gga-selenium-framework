package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.base.IComposite;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JDIAction;
import org.openqa.selenium.Cookie;

/**
 * Created by Roman_Iovlev on 9/3/2015.
 */
public interface IPage extends IComposite {
    Page.StringCheckType url();
    Page.StringCheckType title();
    /** Check that page opened */
    @JDIAction
    void checkOpened();
    /** Opens url specified for page */
    @JDIAction
    void open();
    /** Refresh current page */
    @JDIAction
    void refresh();
    /** Go back to previous page */
    @JDIAction
    void back();
    /** Go forward to next page */
    @JDIAction
    void forward();
    /** Add cookie in browser */
    @JDIAction
    void addCookie(Cookie cookie);
    /** Clear browsers cache */
    @JDIAction
    void clearCache();
}
