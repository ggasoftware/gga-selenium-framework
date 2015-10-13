package com.ggasoftware.jdiuitests.implementation.selenium.elements.composite;

import com.ggasoftware.jdiuitests.implementation.selenium.elements.interfaces.complex.IPopup;
import com.ggasoftware.jdiuitests.core.utils.common.Timer;
import com.ggasoftware.jdiuitests.core.utils.map.MapArray;

import static com.ggasoftware.jdiuitests.implementation.selenium.elements.page_objects.annotations.functions.Functions.*;
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

    public void ok()       { getElement.getButton(OK_BUTTON).click();}
    public void cancel()   { getElement.getButton(CANCEL_BUTTON).click();}
    public void close()    { getElement.getButton(CLOSE_BUTTON).click();}

    protected String getTextAction() { return getWebElement().getText(); }

    public final String getText() {
        return invoker.doJActionResult("Get text", this::getTextAction);
    }
    public final String waitText(String text) {
        return invoker.doJActionResult(format("Wait text contains '%s'", text),
                () -> Timer.getByCondition(this::getTextAction, t -> t.contains(text)));
    }
    public final String waitMatchText(String regEx) {
        return invoker.doJActionResult(format("Wait text match regex '%s'", regEx),
                () -> Timer.getByCondition(this::getTextAction, t -> t.matches(regEx)));
    }
}
