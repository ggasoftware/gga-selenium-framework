package com.ggasoftware.jdiuitests.implementation.selenium.elements.pageobjects.annotations.functions;

/**
 * Created by Roman_Iovlev on 7/21/2015.
 */
public enum Functions {
    NONE(""),
    OK_BUTTON("ok"),
    CLOSE_BUTTON("close"),
    CANCEL_BUTTON("cancel");

    public String name;
    Functions(String name) { this.name = name; }
}
