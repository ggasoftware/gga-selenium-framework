package com.ggasoftware.jdi_ui_tests.core.elements.template.common;

import com.ggasoftware.jdi_ui_tests.core.elements.template.base.Element;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.common.IText;
import com.ggasoftware.jdi_ui_tests.utils.common.Timer;

import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public class Text extends Element implements IText {

    protected String getTextAction();
    protected String getValueAction() { return getTextAction(); }

    public final String getText() {
        return doJActionResult("Get text", this::getTextAction);
    }
    public final String waitText(String text) {
        return doJActionResult(format("Wait text contains '%s'", text),
            () -> Timer.getByCondition(this::getTextAction, t -> t.contains(text)));
    }
    public final String waitMatchText(String regEx) {
        return doJActionResult(format("Wait text match regex '%s'", regEx),
            () -> Timer.getByCondition(this::getTextAction, t -> t.matches(regEx)));
    }
    public final String getValue() { return doJActionResult("Get value", this::getValueAction); }
}
