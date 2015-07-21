package com.epam.ui_test_framework.elements.composite;

import com.epam.ui_test_framework.elements.interfaces.complex.IPopup;
import com.epam.ui_test_framework.elements.common.Button;
import com.epam.ui_test_framework.elements.common.Text;
import org.openqa.selenium.By;

import static com.epam.ui_test_framework.elements.page_objects.annotations.functions.Functions.*;

/**
 * Created by Roman_Iovlev on 7/8/2015.
 */
public class Popup extends Text implements IPopup {
    public Popup() { }
    public Popup(By byLocator) { super(byLocator); }

    @Override
    protected String getTextAction() { return getTextElement().getText(); }
    protected void buttonAction(Button button) {
        button.click();
    }

    public void ok()       { buttonAction(getButton(OK_BUTTON));}
    public void cancel()   { buttonAction(getButton(CANCEL_BUTTON));}
    public void close()    { buttonAction(getButton(CLOSE_BUTTON));}

}
