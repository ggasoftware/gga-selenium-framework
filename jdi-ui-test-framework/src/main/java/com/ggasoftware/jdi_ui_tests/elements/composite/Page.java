package com.ggasoftware.jdi_ui_tests.elements.composite;

import com.ggasoftware.jdi_ui_tests.asserter.testNG.Check;
import com.ggasoftware.jdi_ui_tests.elements.BaseElement;
import com.ggasoftware.jdi_ui_tests.elements.page_objects.annotations.JDIAction;
import com.ggasoftware.jdi_ui_tests.settings.JDISettings;
import org.openqa.selenium.Cookie;

import static java.lang.String.format;

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
        return new StringCheckType(getUrl(), url, urlMatcher);
    }
    public StringCheckType title() { return new StringCheckType(geTtitle(), title, titleMatcher); }
    public void checkOpened() {
        if (urlMatcher != null) {
            try { url().match();
            } catch (Exception ex) { url().contains(); }
        }
        else
            url().check();
        if (titleMatcher != null) {
            try { title().match();
            } catch (Exception ex) { title().contains(); }
        }
        else
            title().check();
    }

    public class StringCheckType {
        private String actual;
        private String expected;
        private String matcher;

        public StringCheckType(String actual, String expected, String matcher) {
            this.actual = actual;
            this.expected = expected;
            this.matcher = (matcher != null) ? matcher : expected;
        }

        /** BaseChecker that current page url/title equals to expected url/title */
        @JDIAction
        public void check() { new Check("Page url equals to " + expected).areEquals(actual, expected); }
        /** BaseChecker that current page url/title matches to expected url/title-matcher */
        @JDIAction
        public void match() { new Check("Page url matches to " + matcher).isTrue(actual.matches(matcher)); }
        /** BaseChecker that current page url/title contains expected url/title-matcher */
        @JDIAction
        public void contains() { new Check("Page url contains " + matcher).isTrue(actual.contains(matcher)); }
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

    public static String getUrl() {
        return JDISettings.getDriver().getCurrentUrl();
    }
    public static String geTtitle() {
        return JDISettings.getDriver().getTitle();
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
