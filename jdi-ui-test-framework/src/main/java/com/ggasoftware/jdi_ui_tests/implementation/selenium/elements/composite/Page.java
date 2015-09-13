package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite;

import com.ggasoftware.jdi_ui_tests.core.settings.JDISettings;
import com.ggasoftware.jdi_ui_tests.core.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.interfaces.complex.IPage;
import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.page_objects.annotations.JDIAction;
import com.ggasoftware.jdi_ui_tests.implementation.testng.asserter.Check;
import org.openqa.selenium.Cookie;

import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/17/2015.
 */
public class Page extends BaseElement implements IPage {
    protected String url;
    protected String title;
    protected String urlContains;
    protected String titleContains;
    protected String urlMatchs;
    protected String titleMatchs;

    public void updatePageData(String url, String title, String urlContains, String titleContains, String urlMatchs, String titleMatchs) {
        if (this.url == null)
            this.url = url;
        if (this.title == null)
            this.title = title;
        if (this.urlContains == null && urlContains != null && !urlContains.equals(""))
            this.urlContains = urlContains;
        if (this.titleContains == null && titleContains != null && !titleContains.equals(""))
            this.titleContains = titleContains;
        if (this.urlMatchs == null && urlMatchs != null && !urlMatchs.equals(""))
            this.urlMatchs = urlMatchs;
        if (this.titleMatchs == null && titleMatchs != null && !titleMatchs.equals(""))
            this.titleMatchs = titleMatchs;
    }
    public static String getUrlFromUri(String uri) {
        return JDISettings.domain.replaceAll("/*$", "") + "/" + uri.replaceAll("^/*", "");
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
        return new StringCheckType(Page::getUrl, url, urlContains, urlMatchs, "url");
    }
    public StringCheckType title() { return new StringCheckType(Page::geTtitle, title, titleContains, titleMatchs, "title"); }

    public static boolean checkAfterOpen = false;
    public void checkOpened() {
        if (urlContains != null)
            url().contains();
        else {
            if (urlMatchs != null)
                url().match();
            else if (url != null && !url.equals(""))
                url().check();
        }
        if (titleContains != null)
            title().contains();
        else {
            if (titleMatchs != null)
                title().match();
            else if (title != null && !title.equals(""))
                title().check();
        }
    }

    public class StringCheckType {
        private JFuncT<String> actual;
        private String equals;
        private String contains;
        private String matches;
        private String what;

        public StringCheckType(JFuncT<String> actual, String equals, String contains, String matches, String what) {
            this.actual = actual;
            this.equals = equals;
            this.contains = contains;
            this.matches = matches;
            this.what = what;
        }

        /** BaseChecker that current page url/title equals to expected url/title */
        @JDIAction
        public void check() { new Check("Page " + what + " equals to " + equals).areEquals(actual, equals); }
        /** BaseChecker that current page url/title matches to expected url/title-matcher */
        @JDIAction
        public void match() { new Check("Page " + what + " matches to " + matches).matches(actual, matches); }
        /** BaseChecker that current page url/title contains expected url/title-matcher */
        @JDIAction
        public void contains() { new Check("Page " + what + " contains " + contains).contains(actual, contains); }
    }

    public void open() {
        invoker.doJAction(format("Open page %s by url %s", getName(), url),
                () -> getDriver().navigate().to(url));
        if (checkAfterOpen)
            checkOpened();
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

    public void refresh() {
        invoker.doJAction("Refresh page " + getName(),
                () -> getDriver().navigate().refresh());
    }
    public void back() {
        invoker.doJAction("Go back to previous page",
                () -> getDriver().navigate().back());
    }
    public void forward() {
        invoker.doJAction("Go forward to next page",
                () -> getDriver().navigate().forward());
    }
    public void addCookie(Cookie cookie) {
        invoker.doJAction("Go forward to next page",
                () -> getDriver().manage().addCookie(cookie));
    }
    public void clearCache() {
        invoker.doJAction("Go forward to next page",
                () -> getDriver().manage().deleteAllCookies());
    }
}
