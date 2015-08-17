package com.ggasoftware.jdi_ui_tests.core.elements.base;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface IDriverElement {
    void click();                           // Operate with element
    String getText();                       // Get text from element
    void clear();                // clear value of element
    void setText(String text);              // Set text in element
    String getAttribute(String attrName);   // Get some elements attributes
    boolean isDisplayed();                  // Available to interact
}
