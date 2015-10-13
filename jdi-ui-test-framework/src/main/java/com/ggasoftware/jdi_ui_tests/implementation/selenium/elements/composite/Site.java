package com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.composite;

import com.ggasoftware.jdi_ui_tests.implementation.selenium.elements.CascadeInit;

/**
 * Created by Roman_Iovlev on 8/30/2015.
 */
public class Site {
    public static <T> void Init(Class<T> site) { CascadeInit.InitPages(site); }

}
