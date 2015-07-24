package com.ggasoftware.uitest.control.interfaces.complex;

import com.ggasoftware.uitest.control.interfaces.base.IComposite;
import com.ggasoftware.uitest.control.new_controls.composite.Page;
import org.openqa.selenium.Cookie;

/**
 * Created by Roman_Iovlev on 7/17/2015.
 */
public interface IPage extends IComposite {
    void open();
    static void openUrl(String url) { new Page(url).open(); }
    void refresh();
    void back();
    void forward();
    void addCookie(Cookie cookie);
    void clearCache();
    void checkUrl();
    void checkTitle();
    void matchUrl(String regEx);
    void matchTitle(String regEx);
    void urlContains(String str);
    void titleContains(String str);
}
