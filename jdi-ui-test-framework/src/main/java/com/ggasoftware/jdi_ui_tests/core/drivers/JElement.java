package com.ggasoftware.jdi_ui_tests.core.drivers;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.base.*;
import com.ggasoftware.jdi_ui_tests.settings.HighlightSettings;

/**
 * Created by Roman_Iovlev on 6/10/2015.
 */
public interface JElement<TDriver, TElement, TLocator> {
    void click();                           // Operate with element
    String getText();                       // Get text from element
    void clear();                // clear value of element
    void setText(String text);              // Set text in element
    String getAttribute(String attrName);   // Get some elements attributes
    boolean isDisplayed();                  // Available to interact
    void highlight(IElement<TElement> element);
    void highlight(IElement<TElement> element, HighlightSettings highlightSettings);
    void processDemoMode(IBaseElement<TDriver, TLocator> element);
}
