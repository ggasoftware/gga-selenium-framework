package com.ggasoftware.jdi_ui_tests.core.elements.composite;

import com.ggasoftware.jdi_ui_tests.core.elements.common.AButton;
import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.IPopup;
import com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.functions.Functions;
import com.ggasoftware.jdi_ui_tests.utils.map.MapArray;

import static com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.functions.Functions.*;
import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public abstract class APopupForm<T> extends AForm<T> implements IPopup {
    @Override
    public void submit(MapArray<String, String> objStrings) { fill(objStrings); ok(); }

    public AButton getButton(Functions function) {
        AButton button = tryGetResult(() -> getButton(function));
        if (button == null) {
            button = tryGetResult(() -> getButton(function.name));
            if (button == null)
                throw asserter.exception(format("Can't find button '%s' for element '%s'", function.name, toString()));
        }
        return button;
    }

    public void ok()       { getButton(OK_BUTTON).click();}
    public void cancel()   { getButton(CANCEL_BUTTON).click();}
    public void close()    { getButton(CLOSE_BUTTON).click();}
}
