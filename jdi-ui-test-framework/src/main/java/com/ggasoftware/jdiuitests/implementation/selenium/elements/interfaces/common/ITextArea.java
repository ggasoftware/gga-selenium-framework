package com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.common;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface ITextArea extends ITextField {
    /** Clear textarea and Input several lines of text in textarea */
    @JDIAction
    void inputLines(String... textLines);
    /** Add text in textarea from new line */
    @JDIAction
    void addNewLine(String textLine);
    /** Get lines of text in textarea */
    @JDIAction
    String[] getLines();
}
