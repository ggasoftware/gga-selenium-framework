package com.ggasoftware.jdiuitest.web.selenium.preconditions;

import com.ggasoftware.jdiuitest.web.selenium.elements.WebSettings;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.WebPage;
import com.ggasoftware.jdiuitest.core.utils.common.Timer;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JAction;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncT;

import static com.ggasoftware.jdiuitest.core.settings.JDISettings.domain;

/**
 * Created by Roman_Iovlev on 10/27/2015.
 */
public interface IPreconditions {
    JFuncT<Boolean> checkAction();
    JAction moveToAction();

    static boolean checkUrl(String uri) {
        return WebPage.getUrl().matches(".*/" + uri + "(\\?.*)?");
    }

    static void openUri(String uri) {
        Timer timer = new Timer(1000, 100);
        WebPage.openUrl(getUrlByUri(uri));
        if (!timer.timeoutPassed())
            WebSettings.getDriver().navigate().to(getUrlByUri(uri));
    }

    static String getUrlByUri(String uri) {
        return domain.replaceAll("/*$", "") + "/" + uri.replaceAll("^/*", "");
    }
    default void open() {
        moveToAction().invoke();
    }
}
