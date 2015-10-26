package com.epam.career.page_objects.enums;

import com.ggasoftware.jdiuitests.core.utils.common.Timer;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JAction;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncT;

import static com.ggasoftware.jdiuitests.core.settings.JDISettings.domain;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.getDriver;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page.getUrl;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page.openUrl;

/**
 * Created by 12345 on 03.06.2015.
 */
public enum Preconditions {
    HOME_PAGE("");

    public JFuncT<Boolean> checkAction;
    public JAction moveToAction;

    Preconditions(JFuncT<Boolean> checkAction, JAction moveToAction) {
        this.checkAction = checkAction;
        this.moveToAction = moveToAction;
    }
    Preconditions(String uri) {
        this(() -> checkUrl(uri), () -> openUri(uri));
    }
    private static boolean checkUrl(String uri) {
        return getUrl().matches(".*/" + uri + "(\\?.*)?");
    }
    private static void openUri(String uri) {
        Timer timer = new Timer(1000, 100);
        openUrl(getUrlByUri(uri));
        if (!timer.timeoutPassed())
            getDriver().navigate().to(getUrlByUri(uri));
    }

    private static String getUrlByUri(String uri) { return domain.replaceAll("/*$", "") + "/" + uri.replaceAll("^/*", ""); }

}
