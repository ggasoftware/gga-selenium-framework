package com.ggasoftware.jdi_ui_tests.core.apiAccessors;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface JElement {
    void click();                           // Operate with webElement
    String getText();                       // Get text from webElement
    void clear(String text);                // clear value of webElement
    void setText(String text);              // Set text in webElement
    String getAttribute(String attrNamr);   // Get some elements attributes
    boolean isDisplayed();                  // Available to interact
}
