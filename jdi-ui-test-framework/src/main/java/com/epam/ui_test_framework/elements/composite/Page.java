package com.epam.ui_test_framework.elements.composite;

import com.epam.ui_test_framework.asserter.TestNGAsserter;
import com.epam.ui_test_framework.elements.BaseElement;
import com.epam.ui_test_framework.elements.interfaces.base.IComposite;
import com.epam.ui_test_framework.elements.interfaces.complex.IPage;
import com.epam.ui_test_framework.settings.FrameworkSettings;
import org.openqa.selenium.Cookie;

import static com.epam.ui_test_framework.settings.FrameworkSettings.asserter;

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

    public void open() {
        getDriver().navigate().to(url);
    }
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
        asserter.areEquals(getDriver().getCurrentUrl(), url);
    }
    public void checkTitle() {
        asserter.areEquals(getDriver().getTitle(), title);
    }
    public void matchUrl(String regEx) {
        asserter.matches(getDriver().getCurrentUrl(), regEx);
    }
    public void matchTitle(String regEx) {
        asserter.matches(getDriver().getTitle(), regEx);
    }
    public void urlContains(String str) {
        asserter.contains(getDriver().getCurrentUrl(), str);
    }
    public void titleContains(String str) {
        asserter.contains(getDriver().getTitle(), str);
    }
}
