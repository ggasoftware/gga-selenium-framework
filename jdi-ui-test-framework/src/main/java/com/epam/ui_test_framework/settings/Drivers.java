package com.epam.ui_test_framework.settings;

/**
 * Created by Roman_Iovlev on 7/31/2015.
 */
public enum Drivers {
    CHROME("chrome"),
    FIREFOX("firefox"),
    IE("ie");

    public String name;
    Drivers(String name) { this.name = name; }

}
