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

    /**
     * @return Check that page opened
     */
    @JDIAction
    void checkOpened();

    /**
     * @return Opens url specified for page
     */
    @JDIAction
    void open();

    /**
     * @return Refresh current page
     */
    @JDIAction
    void refresh();

    /**
     * @return Go back to previous page
     */
    @JDIAction
    void back();

    /**
     * @return Go forward to next page
     */
    @JDIAction
    void forward();

    /**
     * @param cookie Specify cookie
     * @return Add cookie in browser
     */
    @JDIAction
    void addCookie(Cookie cookie);

    /**
     * @return Clear browsers cache
     */
    @JDIAction
    void clearCache();
}
