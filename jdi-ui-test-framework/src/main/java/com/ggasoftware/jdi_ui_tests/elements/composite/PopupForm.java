package com.ggasoftware.jdi_ui_tests.elements.composite;

import com.ggasoftware.jdi_ui_tests.elements.interfaces.complex.IPopup;
import com.ggasoftware.jdi_ui_tests.elements.common.Button;
import com.ggasoftware.jdi_ui_tests.elements.page_objects.annotations.functions.Functions;
import com.ggasoftware.jdi_ui_tests.utils.map.MapArray;

import static com.ggasoftware.jdi_ui_tests.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.utils.usefulUtils.TryCatchUtil.tryGetResult;
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

    public Button getButtonBy(Functions function, String name) {
        Button button = tryGetResult(() -> getButton(function));
        if (button == null)
            button = tryGetResult(() -> getButton(name));
        if (button != null)
            return button;
        else
            asserter.exception(format("Can't find button '%s' for element '%s'", name, toString()));
        return null;
    }

    public void ok()       { getButtonBy(Functions.CANCEL_BUTTON, "ok").click();}
    public void cancel()   { getButtonBy(Functions.CANCEL_BUTTON, "cancel").click();}
    public void close()    { getButtonBy(Functions.CLOSE_BUTTON, "close").click();}
}
