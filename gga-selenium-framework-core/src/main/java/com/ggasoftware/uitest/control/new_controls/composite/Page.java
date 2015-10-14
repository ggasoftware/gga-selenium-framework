package com.ggasoftware.uitest.control.new_controls.composite;

import com.ggasoftware.uitest.control.new_controls.base.BaseElement;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

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

    public Page() {
    }

    public Page(String url) {
        this.url = url;
    }

    public Page(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public static void openUrl(String url) {
        new Page(url).open();
    }

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

    public StringCheckType url() {
        return new StringCheckType(url, urlMatcher, getDriver());
    }

    public StringCheckType title() {
        return new StringCheckType(title, titleMatcher, getDriver());
    }

    public void open() {
        getDriver().navigate().to(url);
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

    public class StringCheckType {
        private String string;
        private String matcher;
        private WebDriver driver;

        public StringCheckType(String string, String matcher, WebDriver driver) {
            this.string = string;
            this.matcher = (matcher != null) ? matcher : string;
            this.driver = driver;
        }

        public void check() {
            assertEquals(driver.getTitle(), string);
        }

        public void match() {
            assertTrue(driver.getTitle().matches(matcher));
        }

        public void contains() {
            assertTrue(driver.getTitle().contains(matcher));
        }
    }
}
