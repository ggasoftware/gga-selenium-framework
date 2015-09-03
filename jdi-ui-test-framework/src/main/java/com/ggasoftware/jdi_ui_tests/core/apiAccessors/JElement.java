package com.ggasoftware.jdi_ui_tests.core.apiAccessors;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface JElement {
    void click();                           // Operate with element
    String getText();                       // Get text from element
    void clear(String text);                // clear value of element
    void setText(String text);              // Set text in element
    String getAttribute(String attrNamr);   // Get some elements attributes
    boolean isDisplayed();                  // Available to interact
}
