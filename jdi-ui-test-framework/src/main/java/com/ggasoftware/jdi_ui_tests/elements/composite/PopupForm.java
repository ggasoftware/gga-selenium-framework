package com.ggasoftware.jdi_ui_tests.elements.composite;

import com.ggasoftware.jdi_ui_tests.elements.interfaces.complex.IPopup;
import com.ggasoftware.jdi_ui_tests.utils.common.Timer;
import com.ggasoftware.jdi_ui_tests.utils.map.MapArray;

import static com.ggasoftware.jdi_ui_tests.elements.page_objects.annotations.functions.Functions.*;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class PopupForm<T> extends Form<T> implements IPopup {

    @Override
    public void submit(MapArray<String, String> objStrings) {
        fill(objStrings);
        ok();
    }

    public void ok()       { getButton(OK_BUTTON).click();}
    public void cancel()   { getButton(CANCEL_BUTTON).click();}
    public void close()    { getButton(CLOSE_BUTTON).click();}

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
