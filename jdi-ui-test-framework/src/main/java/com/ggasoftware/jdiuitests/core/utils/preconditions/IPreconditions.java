package com.ggasoftware.jdiuitests.core.utils.preconditions;

import com.ggasoftware.jdiuitests.core.utils.common.Timer;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JAction;
import com.ggasoftware.jdiuitests.core.utils.linqInterfaces.JFuncT;

import static com.ggasoftware.jdiuitests.core.settings.JDISettings.domain;
import static com.ggasoftware.jdiuitests.core.settings.JDISettings.getDriver;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page.getUrl;
import static com.ggasoftware.jdiuitests.implementation.selenium.elements.composite.Page.openUrl;

/**
 * Created by Roman_Iovlev on 10/27/2015.
 */
public interface IPreconditions {
    JFuncT<Boolean> checkAction();
    JAction moveToAction();

    default boolean checkUrl(String uri) {
        return getUrl().matches(".*/" + uri + "(\\?.*)?");
    }
    default void openUri(String uri) {
        Timer timer = new Timer(1000, 100);
        openUrl(getUrlByUri(uri));
        if (!timer.timeoutPassed())
            getDriver().navigate().to(getUrlByUri(uri));
    }

    default String getUrlByUri(String uri) { return domain.replaceAll("/*$", "") + "/" + uri.replaceAll("^/*", ""); }
}
