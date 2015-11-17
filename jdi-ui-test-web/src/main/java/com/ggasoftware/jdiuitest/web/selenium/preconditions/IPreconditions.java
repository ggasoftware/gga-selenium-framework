/*
 * Copyright 2004-2016 EPAM Systems
 *
 * This file is part of JDI project.
 *
 * JDI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JDI is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty ofMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JDI. If not, see <http://www.gnu.org/licenses/>.
 */
 
package com.ggasoftware.jdiuitest.web.selenium.preconditions;

import com.ggasoftware.jdiuitest.core.utils.common.Timer;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JAction;
import com.ggasoftware.jdiuitest.core.utils.linqinterfaces.JFuncT;
import com.ggasoftware.jdiuitest.web.selenium.elements.WebSettings;
import com.ggasoftware.jdiuitest.web.selenium.elements.composite.WebPage;

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
