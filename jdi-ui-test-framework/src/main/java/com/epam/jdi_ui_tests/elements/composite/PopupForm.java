package com.epam.jdi_ui_tests.elements.composite;

import com.epam.jdi_ui_tests.elements.interfaces.complex.IPopup;
import com.epam.jdi_ui_tests.elements.common.Button;
import com.epam.jdi_ui_tests.elements.page_objects.annotations.functions.Functions;
import com.epam.jdi_ui_tests.utils.map.MapArray;
import org.openqa.selenium.By;

import static com.epam.jdi_ui_tests.elements.page_objects.annotations.functions.Functions.*;
import static com.epam.jdi_ui_tests.settings.FrameworkSettings.asserter;
import static com.epam.jdi_ui_tests.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class PopupForm<T> extends Form<T> implements IPopup {
    public PopupForm() { }
    public PopupForm(By byLocator) { super(byLocator); }

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

    public void ok()       { getButtonBy(CANCEL_BUTTON, "ok").click();}
    public void cancel()   { getButtonBy(CANCEL_BUTTON, "cancel").click();}
    public void close()    { getButtonBy(CLOSE_BUTTON, "close").click();}
}
