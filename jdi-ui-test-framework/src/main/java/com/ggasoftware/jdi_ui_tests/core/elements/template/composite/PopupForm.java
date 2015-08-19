package com.ggasoftware.jdi_ui_tests.core.elements.template.composite;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.IPopup;
import com.ggasoftware.jdi_ui_tests.utils.map.MapArray;

import static com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.functions.Functions.*;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class PopupForm<T> extends Form<T> implements IPopup {
    @Override
    public void submit(MapArray<String, String> objStrings) { fill(objStrings); ok(); }

    public void ok()       { getButton(OK_BUTTON).click();}
    public void cancel()   { getButton(CANCEL_BUTTON).click();}
    public void close()    { getButton(CLOSE_BUTTON).click();}
}
