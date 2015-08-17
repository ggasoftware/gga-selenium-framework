package com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.composite;

import com.ggasoftware.jdi_ui_tests.core.elements.interfaces.complex.IPopup;
import com.ggasoftware.jdi_ui_tests.core.elements.page_objects.annotations.functions.Functions;
import com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.common.Button;
import com.ggasoftware.jdi_ui_tests.implementations.elements.selenium.common.Text;

import static com.ggasoftware.jdi_ui_tests.core.settings.JDISettings.asserter;
import static com.ggasoftware.jdi_ui_tests.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class Popup extends Text implements IPopup {

    @Override
    protected String getTextAction() { return getTextElement().getText(); }

    public Button getButtonBy(Functions function, String name) {
        Button button = tryGetResult(() -> getButton(function));
        if (button == null)
            button = tryGetResult(() -> getButton(name));
        if (button == null)
            throw asserter.exception(format("Can't find button '%s' for element '%s'", name, toString()));
        return button;
    }

    public void ok()       { getButtonBy(Functions.OK_BUTTON, "ok").click();}
    public void cancel()   { getButtonBy(Functions.CANCEL_BUTTON, "cancel").click();}
    public void close()    { getButtonBy(Functions.CLOSE_BUTTON,  "close").click();}

}
