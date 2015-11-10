package com.ggasoftware.jdiuitests.core.apiaccessors;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface JElement {
    void click();                           // Operate with Element

    String getText();                       // Get text from Element

    void setText(String text);              // Set text in Element

    void clear(String text);                // clear value of Element

    String getAttribute(String attrNamr);   // Get some elements attributes

    boolean isDisplayed();                  // Available to interact
}
