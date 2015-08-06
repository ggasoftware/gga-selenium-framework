package com.ggasoftware.jdi_ui_tests.elements.common;

import com.ggasoftware.jdi_ui_tests.elements.base.Element;
import com.ggasoftware.jdi_ui_tests.elements.base.HasValue;
import com.ggasoftware.jdi_ui_tests.elements.interfaces.base.IHasValue;
import com.ggasoftware.jdi_ui_tests.elements.interfaces.common.IText;
import com.ggasoftware.jdi_ui_tests.utils.common.Timer;
import org.openqa.selenium.By;

import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/6/2015.
 */
public class Text extends Element implements IText {
    public Text() { }
    public Text(By byLocator) { super(byLocator); }

    protected IHasValue hasValue() { return new HasValue(this::getTextAction); }
    public final String getValue() { return hasValue().getValue(); }

    protected String getTextAction() { return getWebElement().getText(); }

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
}
