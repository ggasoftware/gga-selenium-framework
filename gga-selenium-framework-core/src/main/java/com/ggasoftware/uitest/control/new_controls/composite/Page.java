package com.ggasoftware.uitest.control.new_controls.composite;

import com.ggasoftware.uitest.control.interfaces.complex.IPage;
import com.ggasoftware.uitest.control.new_controls.base.BaseElement;
import org.openqa.selenium.Cookie;

import static org.testng.Assert.*;
import static org.testng.Assert.assertTrue;

/**
 * Created by Roman_Iovlev on 7/17/2015.
 */
public class Page extends BaseElement implements IPage {
    private String url;
    private String title;
    public String getUrl() { return url; }
    public String getTitle() { return title; }
    public void updatePageData(String url, String title) {
        if (this.url == null)
            this.url = url;
        if (this.title == null)
            this.title = title;
    }

    public Page() {}
    public Page(String url) {
        this.url = url;
    }
    public Page(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public void open() { getDriver().navigate().to(url); }
    public static void openUrl(String url) {
        new Page(url).open();
    }
    public void refresh() {
        getDriver().navigate().refresh();
    }

    public void back() {
        getDriver().navigate().back();
    }
    public void forward() {
        getDriver().navigate().forward();
    }
    public void addCookie(Cookie cookie) {
        getDriver().manage().addCookie(cookie);
    }
    public void clearCache() {
        getDriver().manage().deleteAllCookies();
    }
    public void checkUrl() {
        assertEquals(getDriver().getCurrentUrl(), url);
    }
    public void checkTitle() {
        assertEquals(getDriver().getTitle(), title);
    }
    public void matchUrl(String regEx) {
        assertTrue(getDriver().getCurrentUrl().matches(regEx));
    }
    public void matchTitle(String regEx) {
        assertTrue(getDriver().getTitle().matches(regEx));
    }
    public void urlContains(String str) {
        assertTrue(getDriver().getCurrentUrl().contains(str));
    }
    public void titleContains(String str) {
        assertTrue(getDriver().getTitle().contains(str));
    }
}
