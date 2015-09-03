package com.ggasoftware.jdi_ui_tests.implementation.selenium.driver;

/**
 * Created by Roman_Iovlev on 7/31/2015.
 */
public enum DriverTypes {
    CHROME("chrome"),
    FIREFOX("firefox"),
    IE("ie");

    public String name;
    DriverTypes(String name) { this.name = name; }

}
