package com.ggasoftware.jdi_ui_tests.elements.composite;

import com.ggasoftware.jdi_ui_tests.elements.BaseElement;
import com.ggasoftware.jdi_ui_tests.elements.page_objects.annotations.JDIAction;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import static java.lang.String.format;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by Roman_Iovlev on 7/17/2015.
 */
public class Page extends BaseElement {
    private String url;
    private String title;
    private String urlMatcher;
    private String titleMatcher;
    public void updatePageData(String url, String title, String urlMatcher, String titleMatcher) {
        if (this.url == null)
            this.url = url;
        if (this.title == null)
            this.title = title;
        if (this.urlMatcher == null && urlMatcher != null && !urlMatcher.equals(""))
            this.urlMatcher = urlMatcher;
        if (this.titleMatcher == null && titleMatcher != null && !titleMatcher.equals(""))
            this.titleMatcher = titleMatcher;
    }

    public Page() {}
    public Page(String url) {
        this.url = url;
    }
    public Page(String url, String title) {
        this.url = url;
        this.title = title;
    }
    public StringCheckType url() {
        return new StringCheckType(url, urlMatcher, getDriver());
    }
    public StringCheckType title() {
        return new StringCheckType(title, titleMatcher, getDriver());
    }

    public class StringCheckType {
        private String string;
        private String matcher;
        private WebDriver driver;

        public StringCheckType(String string, String matcher, WebDriver driver) {
            this.string = string;
            this.matcher = (matcher != null) ? matcher : string;
            this.driver = driver;
        }

        /** BaseChecker that current page url/title equals to expected url/title */
        @JDIAction
        public void check() { assertEquals(driver.getTitle(), string); }
        /** BaseChecker that current page url/title matches to expected url/title-matcher */
        @JDIAction
        public void match() { assertTrue(driver.getTitle().matches(matcher)); }
        /** BaseChecker that current page url/title contains expected url/title-matcher */
        @JDIAction
        public void contains() { assertTrue(driver.getTitle().contains(matcher)); }
    }

    /** Opens url specified for page */
    @JDIAction
    public void open() {
        doJAction(format("Open page %s by url %s", getName(), url),
            () -> getDriver().navigate().to(url));
    }
    public static void openUrl(String url) {
        new Page(url).open();
    }
    /** Refresh current page */
    @JDIAction
    public void refresh() {
        doJAction("Refresh page " + getName(),
                () -> getDriver().navigate().refresh());
    }
    /** Go back to previous page */
    @JDIAction
    public void back() {
        doJAction("Go back to previous page",
                () -> getDriver().navigate().back());
    }
    /** Go forward to next page */
    @JDIAction
    public void forward() {
        doJAction("Go forward to next page",
                () -> getDriver().navigate().forward());
    }
    /** Add cookie in browser */
    @JDIAction
    public void addCookie(Cookie cookie) {
        doJAction("Go forward to next page",
                () -> getDriver().manage().addCookie(cookie));
    }
    /** Clear browsers cache */
    @JDIAction
    public void clearCache() {
        doJAction("Go forward to next page",
                () -> getDriver().manage().deleteAllCookies());
    }
}
