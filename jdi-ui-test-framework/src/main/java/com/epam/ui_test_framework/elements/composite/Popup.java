package com.epam.ui_test_framework.elements.composite;

import com.epam.ui_test_framework.elements.interfaces.complex.IPopup;
import com.epam.ui_test_framework.elements.common.Button;
import com.epam.ui_test_framework.elements.common.Text;
import com.epam.ui_test_framework.elements.page_objects.annotations.functions.Functions;
import com.epam.ui_test_framework.settings.FrameworkSettings;
import org.openqa.selenium.By;

import static com.epam.ui_test_framework.elements.page_objects.annotations.functions.Functions.*;
import static com.epam.ui_test_framework.settings.FrameworkSettings.asserter;
import static com.epam.ui_test_framework.utils.usefulUtils.TryCatchUtil.tryGetResult;
import static java.lang.String.format;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class Popup extends Text implements IPopup {
    public Popup() { }
    public Popup(By byLocator) { super(byLocator); }

    @Override
    protected String getTextAction() { return getTextElement().getText(); }

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
    public void close()    { getButtonBy(CLOSE_BUTTON,  "close").click();}

}
