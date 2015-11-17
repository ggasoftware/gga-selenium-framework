package com.ggasoftware.jdiuitest.core.interfaces.common;

import com.ggasoftware.jdiuitest.core.annotations.JDIAction;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public interface ITextArea extends ITextField {
    /**
     * @param textLines Specify text lines (clear textArea before
     * Clear textarea and Input several lines of text in textarea
     */
    @JDIAction
    void inputLines(String... textLines);

    /**
     * @param textLine Specify text to add new line (without clearing previous)
     * Add text in textarea from new line
     */
    @JDIAction
    void addNewLine(String textLine);

    /** @return Get lines of text in textarea */
    @JDIAction
    String[] getLines();
}
