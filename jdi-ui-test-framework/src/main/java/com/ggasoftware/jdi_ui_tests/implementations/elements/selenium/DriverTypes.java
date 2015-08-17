package com.ggasoftware.jdi_ui_tests.implementations.elements.selenium;

/**
 * Created by Roman_Iovlev on 7/31/2015.
 */
public enum DriverTypes {
    CHROME("chrome"),
    FIREFOX("firefox"),
    IE("internet explorer"),
    OPERA("opera"),
    SAFARI("safari"),
    ANDROID("android"),
    IPHONE("iPhone"),
    IPAD("iPad"),
    HTML_UNIT("htmlunit");

    public String name;
    DriverTypes(String name) { this.name = name; }

}
