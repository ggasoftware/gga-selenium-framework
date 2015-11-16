package com.ggasoftware.jdiuitest.web.selenium.elements.composite;

import com.ggasoftware.jdiuitest.web.selenium.elements.WebSettings;
import com.ggasoftware.jdiuitest.web.testng.asserter.Check;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncT;
import com.ggasoftware.jdiuitest.web.selenium.elements.BaseElement;
import com.ggasoftware.jdiuitest.core.interfaces.complex.IPage;
import com.ggasoftware.jdiuitest.core.annotations.JDIAction;
import org.openqa.selenium.Cookie;

import static com.ggasoftware.jdiuitest.core.settings.JDISettings.*;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/17/2015.
 */
public class WebPage extends BaseElement implements IPage {
    public static boolean checkAfterOpen = false;
    public String url;
    public String title;
    protected CheckPageTypes checkUrlType = CheckPageTypes.EQUAL;
    protected CheckPageTypes checkTitleType = CheckPageTypes.EQUAL;
    protected String urlTemplate;

    public WebPage() {
    }

    public WebPage(String url) {
        this.url = url;
    }

    public WebPage(String url, String title) {
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
        new WebPage(url).open();
    }

    public static String getUrl() {
        return WebSettings.getDriver().getCurrentUrl();
    }

    public static String geTtitle() {
        return WebSettings.getDriver().getTitle();
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
        return new StringCheckType(WebPage::getUrl, url, urlTemplate, "url");
    }

    public StringCheckType title() {
        return new StringCheckType(WebPage::geTtitle, title, title, "title");
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

    /**
     * @return Refresh current page
     */
    @JDIAction
    public void refresh() {
        invoker.doJAction("Refresh page " + getName(),
                () -> getDriver().navigate().refresh());
    }

    /**
     * @return Go back to previous page
     */
    @JDIAction
    public void back() {
        invoker.doJAction("Go back to previous page",
                () -> getDriver().navigate().back());
    }


    /**
     * @return Go forward to next page
     */
    @JDIAction
    public void forward() {
        invoker.doJAction("Go forward to next page",
                () -> getDriver().navigate().forward());
    }

    /**
     * @param cookie Specify cookie
     * @return Add cookie in browser
     */
    @JDIAction
    public void addCookie(Cookie cookie) {
        invoker.doJAction("Go forward to next page",
                () -> getDriver().manage().addCookie(cookie));
    }

    /**
     * @return Clear browsers cache
     */
    @JDIAction
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
