package com.epam.ui_test_framework.elements.composite;

import com.epam.ui_test_framework.elements.interfaces.complex.IPopup;
import com.epam.ui_test_framework.elements.common.Button;
import com.epam.ui_test_framework.utils.map.MapArray;
import org.openqa.selenium.By;

import static com.epam.ui_test_framework.elements.page_objects.annotations.functions.Functions.*;

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
    protected void buttonAction(Button button) { button.click(); }
    public void ok()       { buttonAction(getButton(OK_BUTTON));}
    public void cancel()   { buttonAction(getButton(CANCEL_BUTTON));}
    public void close()         { buttonAction(getButton(CLOSE_BUTTON));}
}
