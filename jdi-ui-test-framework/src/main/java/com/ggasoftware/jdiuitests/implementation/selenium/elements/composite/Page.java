package com.ggasoftware.jdiuitests.implementation.selenium.elements.composite;

import com.ggasoftware.jdiuitests.core.settings.JDISettings;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncT;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.BaseElement;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.IPage;
import com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.JDIAction;
import com.ggasoftware.jdiuitests.implementation.testng.asserter.Check;
import org.openqa.selenium.Cookie;

import static com.ggasoftware.jdiuitests.core.settings.JDISettings.*;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.CheckPageTypes.EQUAL;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/17/2015.
 */
public class Page extends BaseElement implements IPage {
    public static boolean checkAfterOpen = false;
    public String url;
    public String title;
    protected CheckPageTypes checkUrlType = EQUAL;
    protected CheckPageTypes checkTitleType = EQUAL;
    protected String urlTemplate;

    public Page() {
    }

    public Page(String url) {
        this.url = url;
    }

    public Page(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public static String getUrlFromUri(String uri) {
        return domain.replaceAll("/*$", "") + "/" + uri.replaceAll("^/*", "");
    }

    public static String getMatchFromDomain(String uri) {
        return domain.replaceAll("/*$", "").replace(".", "\\.") + "/" + uri.replaceAll("^/*", "");
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

    public void updatePageData(String url, String title, CheckPageTypes checkUrlType, CheckPageTypes checkTitleType, String urlTemplate) {
        if (this.url == null)
            this.url = url;
        if (this.title == null)
            this.title = title;
        this.checkUrlType = checkUrlType;
        this.checkTitleType = checkTitleType;
        this.urlTemplate = urlTemplate;
    }

    public StringCheckType url() {
        return new StringCheckType(Page::getUrl, url, urlTemplate, "url");
    }

    public StringCheckType title() {
        return new StringCheckType(Page::geTtitle, title, title, "title");
    }

    public void checkOpened() {
        switch (checkUrlType) {
            case EQUAL:
                url().check();
                break;
            case MATCH:
                url().match();
                break;
            case CONTAIN:
                url().contains();
                break;
        }
        switch (checkTitleType) {
            case EQUAL:
                title().check();
                break;
            case MATCH:
                title().match();
                break;
            case CONTAIN:
                title().contains();
                break;
        }
    }

    public void open() {
        invoker.doJAction(format("Open page %s by url %s", getName(), url),
                () -> getDriver().navigate().to(url));
        if (checkAfterOpen)
            checkOpened();
    }

    public void isOpened() {
        try {
            logger.test("Page %s is opened", getName());
            if (getUrl().equals(url)) return;
            open();
        } catch (Exception ex) {
            throw exception(format("Can't open page %s. Exception: %s", getName(), ex.getMessage()));
        }

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

    public class StringCheckType {
        private JFuncT<String> actual;
        private String equals;
        private String template;
        private String what;

        public StringCheckType(JFuncT<String> actual, String equals, String template, String what) {
            this.actual = actual;
            this.equals = equals;
            this.template = template;
            this.what = what;
        }

        /**
         * BaseChecker that current page url/title equals to expected url/title
         */
        @JDIAction
        public void check() {
            new Check(format("Page %s equals to '%s'", what, equals)).areEquals(actual, equals);
        }

        /**
         * BaseChecker that current page url/title matches to expected url/title-matcher
         */
        @JDIAction
        public void match() {
            new Check(format("Page %s matches to '%s'", what, template)).matches(actual, template);
        }

        /**
         * BaseChecker that current page url/title contains expected url/title-matcher
         */
        @JDIAction
        public void contains() {
            new Check(format("Page %s contains '%s'", what, template)).contains(actual, template);
        }
    }
}
