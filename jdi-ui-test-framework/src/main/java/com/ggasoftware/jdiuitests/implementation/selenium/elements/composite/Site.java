package com.ggasoftware.jdiuitests.implementation.selenium.elements.composite;

import static com.ggasoftware.jdiuitests.implementation.selenium.elements.CascadeInit.InitPages;

/**
 * Created by Roman_Iovlev on 8/30/2015.
 */
public class Site {
    public static <T> void Init(Class<T> site) { InitPages(site); }

}
